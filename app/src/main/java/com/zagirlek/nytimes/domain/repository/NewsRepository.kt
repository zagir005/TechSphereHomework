package com.zagirlek.nytimes.domain.repository

import androidx.paging.PagingData
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.core.model.NewsFilter
import com.zagirlek.nytimes.data.network.news.dto.NewsPageDTO
import com.zagirlek.nytimes.domain.model.ArticleFull
import com.zagirlek.nytimes.domain.model.ArticleLite
import com.zagirlek.nytimes.domain.model.ArticlesByCategory
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getLatestNews(
        category: NewsCategory? = null,
        page: String? = null,
        titleQuery: String? = null,
    ): Result<NewsPageDTO>

    suspend fun getFullArticleById(
        articleId: String
    ): Result<ArticleFull>

    fun getPagedNews(
        filter: NewsFilter = NewsFilter()
    ): Flow<PagingData<ArticleLite>>

    fun observeFavoriteGroupedByCategory(): Flow<List<ArticlesByCategory>>
}