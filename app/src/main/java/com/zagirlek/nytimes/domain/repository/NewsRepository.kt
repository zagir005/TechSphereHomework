package com.zagirlek.nytimes.domain.repository

import com.zagirlek.nytimes.domain.model.ArticleFull
import com.zagirlek.nytimes.domain.model.NewsCategory
import com.zagirlek.nytimes.domain.model.NewsPage

interface NewsRepository {
    suspend fun getLatestNews(
        category: List<NewsCategory> = emptyList(),
        page: String = "",
        titleQuery: String = "",
    ): Result<NewsPage>

    suspend fun getFullArticleById(
        articleId: String
    ): Result<ArticleFull>
}