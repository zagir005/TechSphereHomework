package com.zagirlek.nytimes.data.repository

import com.zagirlek.nytimes.core.utils.runCatchingCancellable
import com.zagirlek.nytimes.data.local.news.dao.ArticleFullDao
import com.zagirlek.nytimes.data.local.news.dao.ArticleLiteDao
import com.zagirlek.nytimes.data.local.news.dao.ArticleStatusDao
import com.zagirlek.nytimes.data.mapper.firstToDomain
import com.zagirlek.nytimes.data.mapper.toArticleFull
import com.zagirlek.nytimes.data.mapper.toArticleWithStatus
import com.zagirlek.nytimes.data.mapper.toDomain
import com.zagirlek.nytimes.data.mapper.toEntity
import com.zagirlek.nytimes.data.network.extractor.RemoteNewsExtractorSource
import com.zagirlek.nytimes.data.network.news.RemoteNewsSource
import com.zagirlek.nytimes.domain.model.ArticleFullWithStatus
import com.zagirlek.nytimes.domain.model.ArticleLite
import com.zagirlek.nytimes.domain.repository.ArticleRepository

class ArticleRepositoryImpl(
    private val articleFullDao: ArticleFullDao,
    private val articleStatusDao: ArticleStatusDao,
    private val articleLiteDao: ArticleLiteDao,
    private val remoteNewsSource: RemoteNewsSource,
    private val remoteExtractorNewsSource: RemoteNewsExtractorSource
): ArticleRepository {
    override suspend fun getFullArticleById(articleId: String): Result<ArticleFullWithStatus> = runCatchingCancellable {
        val fullArticle = articleFullDao.getById(articleId = articleId)
        val articleStatus = articleStatusDao.getArticleStatusById(articleId = articleId)

        if (fullArticle != null){
            fullArticle
                .toDomain()
                .toArticleWithStatus(
                    isFavorite = articleStatus?.isFavorite,
                    isRead = articleStatus?.isRead
                )
        }else {
            val articleLite: ArticleLite =
                articleLiteDao.getById(articleId)?.toDomain() ?: remoteNewsSource
                    .getLatestNews(id = articleId)
                    .getOrThrow()
                    .firstToDomain()

            val extractedText = remoteExtractorNewsSource
                .extractArticleText(articleLite.link)
                .getOrThrow()
                .text

            val fullArticle = articleLite.toArticleFull(extractedText)

            articleFullDao.insert(article = fullArticle.toEntity())

            fullArticle.toArticleWithStatus(
                isFavorite = articleStatus?.isFavorite,
                isRead = articleStatus?.isRead
            )
        }
    }
}