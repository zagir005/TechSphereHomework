package com.zagirlek.nytimes.data.repository

import com.zagirlek.nytimes.core.utils.runCatchingCancellable
import com.zagirlek.nytimes.data.newsmanager.NewsManager
import com.zagirlek.nytimes.domain.model.ArticleFull
import com.zagirlek.nytimes.domain.model.NewsCategory
import com.zagirlek.nytimes.domain.model.NewsPage
import com.zagirlek.nytimes.domain.repository.NewsRepository

class NewsRepositoryImpl(
    private val newsManager: NewsManager
): NewsRepository {
    override suspend fun getLatestNews(
        category: List<NewsCategory>,
        page: String,
        titleQuery: String
    ): Result<NewsPage> = runCatchingCancellable {
        newsManager.getLatestNews(
            category = category,
            page = page,
            titleQuery = titleQuery
        )
    }

    override suspend fun getFullArticleById(articleId: String): Result<ArticleFull> = runCatchingCancellable {
        newsManager.getFullArticleById(articleId = articleId)
    }
}