package com.zagirlek.nytimes.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.core.model.NewsFilter
import com.zagirlek.nytimes.core.utils.runCatchingCancellable
import com.zagirlek.nytimes.data.local.NyTimesDatabase
import com.zagirlek.nytimes.data.mapper.toDomain
import com.zagirlek.nytimes.data.network.news.dto.NewsPageDTO
import com.zagirlek.nytimes.data.newsmanager.NewsManager
import com.zagirlek.nytimes.data.pagermediator.NewsRemoteMediator
import com.zagirlek.nytimes.domain.model.ArticleFull
import com.zagirlek.nytimes.domain.model.ArticleLite
import com.zagirlek.nytimes.domain.model.ArticleStatus
import com.zagirlek.nytimes.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val database: NyTimesDatabase,
    private val newsManager: NewsManager
): NewsRepository {

    private val articleDao = database.articleDao()
    private val articleStatusDao = database.articleStatusDao()

    override suspend fun getLatestNews(
        category: NewsCategory?,
        page: String?,
        titleQuery: String?
    ): Result<NewsPageDTO> = runCatchingCancellable {
        newsManager.getLatestNews(
            category = category,
            page = page,
            titleQuery = titleQuery?.ifBlank { null }
        )
    }

    override suspend fun getFullArticleById(articleId: String): Result<ArticleFull> =
        runCatchingCancellable {
            newsManager.getFullArticleById(articleId = articleId)
        }

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedNews(filter: NewsFilter): Flow<PagingData<ArticleLite>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false,
                initialLoadSize = 15
            ),
            remoteMediator = NewsRemoteMediator(
                filters = filter,
                database = database,
                newsManager = newsManager
            ),
            pagingSourceFactory = {
                articleDao.getArticlesPagingSource(
                    category = filter.category,
                    titleQuery = filter.titleQuery
                )
            }
        ).flow.map {
            it.map { article ->
                article.toDomain()
            }
        }
    }

    override suspend fun updateArticleStatus(
        articleId: String,
        isFavorite: Boolean,
        isRead: Boolean
    ) {
        articleStatusDao.updateArticleStatus(
            articleId = articleId,
            isRead = isRead,
            isFavorite = isFavorite
        )
    }


    override fun getArticleListStatusFlow(): Flow<List<ArticleStatus>> =
        articleStatusDao.getArticleStatusFlow().map {
            it.map { articleInfo ->
                articleInfo.toDomain()
            }
        }


}