package com.zagirlek.nytimes.data.pagermediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.core.model.NewsFilter
import com.zagirlek.nytimes.data.local.NyTimesDatabase
import com.zagirlek.nytimes.data.local.news.dao.ArticleLiteDao
import com.zagirlek.nytimes.data.local.news.dao.RemoteKeyDao
import com.zagirlek.nytimes.data.local.news.entity.ArticleLiteWithStatusEntity
import com.zagirlek.nytimes.data.local.news.entity.RemoteKeyEntity
import com.zagirlek.nytimes.data.mapper.toEntity
import com.zagirlek.nytimes.data.network.news.RemoteNewsSource

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val category: NewsCategory?,
    private val titleQuery: String?,
    private val database: NyTimesDatabase,
    private val remoteNewsSource: RemoteNewsSource
) : RemoteMediator<Int, ArticleLiteWithStatusEntity>() {

    private val remoteKeyDao: RemoteKeyDao = database.remoteKeyDao()
    private val articleLiteDao: ArticleLiteDao = database.articleLiteDao()
    private val filters = NewsFilter(
        category = category,
        titleQuery = titleQuery
    )

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleLiteWithStatusEntity>
    ): MediatorResult {
        val pageKey = when (loadType) {
            LoadType.REFRESH -> null
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val key = remoteKeyDao.remoteKeysByFilters(filters)?.nextPageKey
                if (key == null) return MediatorResult.Success(endOfPaginationReached = true)
                key
            }
        }

        remoteNewsSource.getLatestNews(
            category = filters.category,
            page = pageKey,
            titleQuery = filters.titleQuery
        ).onSuccess { response ->
            val nextPageKey = response.nextPage

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    articleLiteDao.deleteAll()
                }
                articleLiteDao.insertArticles(response.newsList.map { it.toEntity() })
                remoteKeyDao.insertOrReplace(
                    RemoteKeyEntity(
                        filters = filters,
                        nextPageKey = nextPageKey
                    )
                )
            }
            return MediatorResult.Success(endOfPaginationReached = nextPageKey == null)
        }.onFailure {
            return MediatorResult.Error(it)
        }
        throw IllegalStateException()
    }
}