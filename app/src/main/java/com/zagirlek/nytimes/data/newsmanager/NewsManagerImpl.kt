package com.zagirlek.nytimes.data.newsmanager

import com.zagirlek.nytimes.BuildConfig
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.data.mapper.toArticleFull
import com.zagirlek.nytimes.data.mapper.toDomain
import com.zagirlek.nytimes.data.network.extractor.service.ExtractorService
import com.zagirlek.nytimes.data.network.news.dto.NewsPageDTO
import com.zagirlek.nytimes.data.network.news.service.NewsService
import com.zagirlek.nytimes.domain.model.ArticleFull

class NewsManagerImpl(
    private val newsService: NewsService,
    private val newsExtractorService: ExtractorService
): NewsManager {

    override suspend fun getLatestNews(
        category: NewsCategory?,
        page: String?,
        titleQuery: String?
    ): NewsPageDTO =
        newsService.latest(
            category = category?.let { listOf(it.name.lowercase()) },
            domain = BuildConfig.AVAILABLE_DOMAINS,
            page = page,
            titleQuery = titleQuery
        )

    override suspend fun getFullArticleById(articleId: String): ArticleFull {
        val article = newsService.latest(
            id = articleId
        ).newsList.first().toDomain()
        val fullText = newsExtractorService.extractByUrl(article.link)
        return article.toArticleFull(fullText.article.text)
    }
}