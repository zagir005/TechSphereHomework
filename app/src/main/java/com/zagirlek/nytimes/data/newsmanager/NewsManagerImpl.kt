package com.zagirlek.nytimes.data.newsmanager

import com.zagirlek.nytimes.BuildConfig
import com.zagirlek.nytimes.data.network.extractor.service.ExtractorService
import com.zagirlek.nytimes.data.network.news.service.NewsService
import com.zagirlek.nytimes.data.newsmanager.mapper.toArticleFull
import com.zagirlek.nytimes.data.newsmanager.mapper.toArticleLite
import com.zagirlek.nytimes.data.newsmanager.mapper.toNewsPage
import com.zagirlek.nytimes.domain.model.ArticleFull
import com.zagirlek.nytimes.domain.model.NewsCategory
import com.zagirlek.nytimes.domain.model.NewsPage

class NewsManagerImpl(
    private val newsService: NewsService,
    private val newsExtractorService: ExtractorService
): NewsManager {
    override suspend fun getLatestNews(
        category: List<NewsCategory>,
        page: String,
        titleQuery: String
    ): NewsPage =
        newsService.latest(
            category = category.toList().ifEmpty { null },
            domain = BuildConfig.AVAILABLE_DOMAINS,
            page = page,
            titleQuery = titleQuery
        ).toNewsPage()

    override suspend fun getFullArticleById(articleId: String): ArticleFull {
        val article = newsService.latest(
            id = articleId
        ).newsList.first().toArticleLite()
        val fullText = newsExtractorService.extractByUrl(article.link)
        return article.toArticleFull(fullText.article.text)
    }
}