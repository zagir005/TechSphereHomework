package com.zagirlek.nytimes.data.repository

import com.zagirlek.nytimes.core.error.AppError
import com.zagirlek.nytimes.core.networkchecker.NetworkConnectionChecker
import com.zagirlek.nytimes.data.local.news.dao.ArticleFullDao
import com.zagirlek.nytimes.data.local.news.dao.ArticleLiteDao
import com.zagirlek.nytimes.data.local.news.dao.ArticleStatusDao
import com.zagirlek.nytimes.data.mapper.toArticleFull
import com.zagirlek.nytimes.data.mapper.toArticleWithStatus
import com.zagirlek.nytimes.data.mapper.toDomain
import com.zagirlek.nytimes.data.mapper.toEntity
import com.zagirlek.nytimes.data.network.extractor.RemoteNewsExtractorSource
import com.zagirlek.nytimes.data.network.news.RemoteNewsSource
import com.zagirlek.nytimes.domain.model.ArticleFullWithStatus
import com.zagirlek.nytimes.domain.model.ArticleLite
import com.zagirlek.nytimes.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ArticleRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val articleFullDao: ArticleFullDao,
    private val articleStatusDao: ArticleStatusDao,
    private val articleLiteDao: ArticleLiteDao,
    private val remoteNewsSource: RemoteNewsSource,
    private val remoteExtractorNewsSource: RemoteNewsExtractorSource,
): ArticleRepository {
    override suspend fun getOrLoadFullArticleById(articleId: String): Result<ArticleFullWithStatus> {
        val fullArticle = articleFullDao.getById(articleId = articleId)
        val articleStatus = articleStatusDao.getArticleStatusById(articleId = articleId)

        return if (fullArticle != null){
            Result.success(
                fullArticle
                    .toDomain()
                    .toArticleWithStatus(
                        isFavorite = articleStatus?.isFavorite,
                        isRead = articleStatus?.isRead
                    )
            )
        } else {
            val articleLite: ArticleLite =
                articleLiteDao.getById(articleId)?.toDomain() ?: remoteNewsSource
                    .getLatestNews(id = articleId)
                    .getOrElse { return Result.failure(it)}
                    .newsList.first().toDomain()

            val extractedText = remoteExtractorNewsSource
                .extractArticleText(articleLite.link)
                .getOrElse { return Result.failure(it)}
                .text

            val fullArticle = articleLite.toArticleFull(extractedText)

            articleFullDao.insert(article = fullArticle.toEntity())

            Result.success(
                fullArticle.toArticleWithStatus(
                    isFavorite = articleStatus?.isFavorite,
                    isRead = articleStatus?.isRead
                )
            )
        }
    }

    override suspend fun getOrLoadFullArticleByIdFlow(articleId: String): Flow<Result<ArticleFullWithStatus>> {
        getOrLoadFullArticleById(articleId)
            .onFailure {
                return flowOf(value =
                    if (!networkConnectionChecker.checkConnection())
                        Result.failure(AppError.NoNetworkConnection)
                    else
                        Result.failure(it)
                )
            }

        return articleFullDao
            .getArticleFullWithStatusByIdFlow(articleId)
            .map { entity ->
                if (entity != null)
                    Result.success(entity.toDomain())
                else
                    Result.failure(NoSuchElementException("Article not found"))
            }
    }
}