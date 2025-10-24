package com.zagirlek.news.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.zagirlek.common.model.NewsCategory
import com.zagirlek.local.news.dao.ArticleLiteDao
import com.zagirlek.news.mapper.toDomain
import com.zagirlek.news.model.ArticleLiteWithStatus
import com.zagirlek.news.pagermediator.NewsRemoteMediator
import com.zagirlek.remote.news.source.RemoteNewsSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class NewsRepositoryImpl(
    private val articleLiteDao: ArticleLiteDao,
    private val remoteNewsSource: RemoteNewsSource
): NewsRepository {
    override fun getNewsPager(
        category: NewsCategory?,
        titleQuery: String?
    ): Flow<PagingData<ArticleLiteWithStatus>> {
        val mediator = NewsRemoteMediator(
            category = category,
            titleQuery = titleQuery,
            articleLiteDao = articleLiteDao,
            remoteNewsSource = remoteNewsSource
        )
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            remoteMediator = mediator,
            pagingSourceFactory = {
                articleLiteDao.getArticlesWithStatusPaging(
                    titleQuery,
                    category
                )
            }
        )
            .flow
            .map { pagingData ->
                pagingData.map { articleLiteDao ->
                    articleLiteDao.toDomain()
                }
            }
    }

    override fun getFavoriteNewsFlow(
        category: NewsCategory?,
        titleQuery: String?
    ): Flow<List<ArticleLiteWithStatus>> {
        return articleLiteDao.getFavoriteArticlesFlow(titleQuery, category)
            .map { list ->
                list.map { article ->
                    article.toDomain()
                }
            }
    }
}