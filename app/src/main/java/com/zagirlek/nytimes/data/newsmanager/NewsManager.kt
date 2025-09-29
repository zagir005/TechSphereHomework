package com.zagirlek.nytimes.data.newsmanager

import com.zagirlek.nytimes.domain.model.ArticleFull
import com.zagirlek.nytimes.domain.model.NewsCategory
import com.zagirlek.nytimes.domain.model.NewsPage

interface NewsManager {
    suspend fun getLatestNews(
        category: List<NewsCategory> = emptyList(),
        page: String = "",
        titleQuery: String = "",
    ): NewsPage

    suspend fun getFullArticleById(
        articleId: String
    ): ArticleFull
}