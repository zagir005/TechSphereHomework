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
import com.zagirlek.nytimes.domain.model.ArticlesByCategory
import com.zagirlek.nytimes.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val database: NyTimesDatabase,
    private val newsManager: NewsManager
): NewsRepository {

    private val articleDao = database.articleDao()

    override suspend fun getLatestNews(
        category: NewsCategory?,
        page: String?,
        titleQuery: String?
    ): Result<NewsPageDTO> = runCatchingCancellable {
        newsManager.getLatestNews(
            category = category,
            page = page,
            titleQuery = titleQuery
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
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 40
            ),
            remoteMediator = NewsRemoteMediator(
                filters = filter,
                database = database,
                newsManager = newsManager
            ),
            pagingSourceFactory = {
                articleDao.getArticlesPagingSource(
                    category = filter.category,
                    titleQuery = filter.titleQuery,
                    isFavorite = null,
                    isRead = null
                )
            }
        ).flow.map {
            it.map { article ->
                article.toDomain()
            }
        }
    }

    override fun observeFavoriteGroupedByCategory(): Flow<List<ArticlesByCategory>> {
        return articleDao
            .getArticlesFlow(
                category = null,
                titleQuery = "",
                isFavorite = true,
                isRead = null
            )
            .map { list ->
                list.groupBy { it.category }
                    .toSortedMap(compareBy { it.name })
                    .map { (category, articles) ->
                        ArticlesByCategory(category = category, articles = articles.sortedByDescending { it.pubDate })
                    }
            }
    }
}