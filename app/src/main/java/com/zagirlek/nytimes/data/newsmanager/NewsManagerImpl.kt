package com.zagirlek.nytimes.data.newsmanager

import com.zagirlek.nytimes.BuildConfig
import com.zagirlek.nytimes.core.error.ServerError
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.data.mapper.toArticleFull
import com.zagirlek.nytimes.data.mapper.toDomain
import com.zagirlek.nytimes.data.network.extractor.service.ExtractorService
import com.zagirlek.nytimes.data.network.news.dto.NewsPageDTO
import com.zagirlek.nytimes.data.network.news.service.NewsService
import com.zagirlek.nytimes.domain.model.ArticleFull
import retrofit2.HttpException

class NewsManagerImpl(
    private val newsService: NewsService,
    private val newsExtractorService: ExtractorService
): NewsManager {

    override suspend fun getLatestNews(
        category: NewsCategory?,
        page: String?,
        titleQuery: String?
    ): NewsPageDTO {
        return try {
            newsService.latest(
                category = category?.let { listOf(it.name.lowercase()) },
                domain = BuildConfig.AVAILABLE_DOMAINS,
                page = page,
                titleQuery = titleQuery
            )
        }catch (e: HttpException){
            throw e.toServerError() ?: e
        }
    }

    override suspend fun getFullArticleById(articleId: String): ArticleFull {
        val article = newsService.latest(
            id = articleId
        ).newsList.first().toDomain()
        val fullText = newsExtractorService.extractByUrl(article.link)
        return article.toArticleFull(fullText.article.text)
    }

    fun HttpException.toServerError(): ServerError? =
        when(this.code()){
            429 -> ServerError("Слишком много запросов к API. Попробуйте позже")
            else -> null
        }
}