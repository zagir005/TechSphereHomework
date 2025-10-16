package com.zagirlek.nytimes.data.pagermediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.data.local.NyTimesDatabase
import com.zagirlek.nytimes.data.local.news.dao.ArticleLiteDao
import com.zagirlek.nytimes.data.local.news.entity.ArticleLiteWithStatusEntity
import com.zagirlek.nytimes.data.mapper.toEntity
import com.zagirlek.nytimes.data.network.news.RemoteNewsSource

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    database: NyTimesDatabase,
    private val category: NewsCategory?,
    private val titleQuery: String?,
    private val remoteNewsSource: RemoteNewsSource
) : RemoteMediator<Int, ArticleLiteWithStatusEntity>() {
    private val articleLiteDao: ArticleLiteDao = database.articleLiteDao()
    private var currKey: String? = null

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleLiteWithStatusEntity>
    ): MediatorResult {
        val pageKey = when (loadType) {
            LoadType.REFRESH -> currKey
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                if (currKey == null) return MediatorResult.Success(endOfPaginationReached = true)
                currKey
            }
        }

        remoteNewsSource.getLatestNews(
            category = category,
            page = pageKey,
            titleQuery = titleQuery
        ).onSuccess { response ->
            val nextPageKey = response.nextPage

            if (loadType == LoadType.REFRESH) {
                articleLiteDao.deleteAllExceptWithStatus()
                currKey = null
            }
            articleLiteDao.insertArticles(response.newsList.map { it.toEntity() })
            currKey = nextPageKey

            return MediatorResult.Success(endOfPaginationReached = nextPageKey == null)
        }.onFailure {
            return MediatorResult.Error(it)
        }

        throw IllegalStateException()
    }
}