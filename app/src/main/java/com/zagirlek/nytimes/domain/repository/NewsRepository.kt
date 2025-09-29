package com.zagirlek.nytimes.domain.repository

import com.zagirlek.nytimes.domain.model.ArticleFull
import com.zagirlek.nytimes.domain.model.NewsCategory
import com.zagirlek.nytimes.domain.model.NewsPage

interface NewsRepository {
    fun getLatestNews(
        category: List<NewsCategory> = emptyList(),
        page: String = "",
        titleQuery: String = "",
    ): Result<NewsPage>

    fun getFullArticleById(
        articleId: String
    ): Result<ArticleFull>
}