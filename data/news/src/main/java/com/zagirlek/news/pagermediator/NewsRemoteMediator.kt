package com.zagirlek.news.pagermediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.zagirlek.common.model.NewsCategory
import com.zagirlek.local.news.dao.ArticleLiteDao
import com.zagirlek.local.news.entity.ArticleLiteWithStatusEntity
import com.zagirlek.news.mapper.toEntity
import com.zagirlek.remote.news.RemoteNewsSource

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val articleLiteDao: ArticleLiteDao,
    private val category: NewsCategory?,
    private val titleQuery: String?,
    private val remoteNewsSource: RemoteNewsSource
): RemoteMediator<Int, ArticleLiteWithStatusEntity>() {
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