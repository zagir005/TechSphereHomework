package com.zagirlek.nytimes.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.data.local.NyTimesDatabase
import com.zagirlek.nytimes.data.mapper.toDomain
import com.zagirlek.nytimes.data.network.news.RemoteNewsSource
import com.zagirlek.nytimes.data.pagermediator.NewsRemoteMediator
import com.zagirlek.nytimes.domain.model.ArticleLiteWithStatus
import com.zagirlek.nytimes.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class NewsRepositoryImpl(
    private val database: NyTimesDatabase,
    private val remoteNewsSource: RemoteNewsSource
): NewsRepository {
    private val articleLiteDao = database.articleLiteDao()

    override fun getNewsPager(
        category: NewsCategory?,
        titleQuery: String?
    ): Flow<PagingData<ArticleLiteWithStatus>> {
        val mediator = NewsRemoteMediator(
            category = category,
            titleQuery = titleQuery,
            database = database,
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