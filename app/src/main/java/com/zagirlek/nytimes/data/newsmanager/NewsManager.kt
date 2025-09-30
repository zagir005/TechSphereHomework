package com.zagirlek.nytimes.data.newsmanager

import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.data.network.news.dto.NewsPageDTO
import com.zagirlek.nytimes.domain.model.ArticleFull

interface NewsManager {

    suspend fun getLatestNews(
        category: NewsCategory? = null,
        page: String = "",
        titleQuery: String = "",
    ): NewsPageDTO

    suspend fun getFullArticleById(
        articleId: String
    ): ArticleFull
}