package com.zagirlek.nytimes.data.repository

import com.zagirlek.nytimes.data.local.news.dao.ArticleStatusDao
import com.zagirlek.nytimes.data.local.news.entity.ArticleStatusEntity
import com.zagirlek.nytimes.data.mapper.toDomain
import com.zagirlek.nytimes.domain.model.ArticleStatus
import com.zagirlek.nytimes.domain.repository.ArticleStatusRepository

class ArticleStatusRepositoryImpl(
    private val articleStatusDao: ArticleStatusDao
): ArticleStatusRepository {
    override suspend fun getOrCreateArticleStatusById(articleId: String): ArticleStatus {
        val articleStatus = articleStatusDao.getArticleStatusById(articleId)?.toDomain()

        return if(articleStatus == null){
            articleStatusDao.upsertArticle(
                ArticleStatusEntity(
                    articleId = articleId,
                    isFavorite = false,
                    isRead = false
                )
            )
            articleStatusDao.getArticleStatusById(articleId)?.toDomain()
                ?: throw IllegalStateException("ArticleStatus создан, но почему-то не найден")
        } else articleStatus
    }

    override suspend fun toggleFavoriteStatus(articleId: String) {
        val articleStatus = getOrCreateArticleStatusById(articleId = articleId)
        articleStatusDao.updateArticleStatus(
            articleId = articleStatus.articleId,
            isRead = articleStatus.isRead,
            isFavorite = !articleStatus.isFavorite
        )
    }

    override suspend fun toggleReadStatus(articleId: String) {
        val articleStatus = getOrCreateArticleStatusById(articleId = articleId)
        articleStatusDao.updateArticleStatus(
            articleId = articleStatus.articleId,
            isRead = !articleStatus.isRead,
            isFavorite = articleStatus.isFavorite
        )
    }
}