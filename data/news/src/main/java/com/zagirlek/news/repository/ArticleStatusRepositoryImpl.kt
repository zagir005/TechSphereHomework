package com.zagirlek.news.repository

import com.zagirlek.local.news.dao.ArticleStatusDao
import com.zagirlek.news.repository.ArticleRepository
import com.zagirlek.news.repository.ArticleStatusRepository

class ArticleStatusRepositoryImpl(
    private val articleStatusDao: ArticleStatusDao,
    private val articleRepository: ArticleRepository
): ArticleStatusRepository {

    override suspend fun toggleFavoriteStatus(articleId: String): Result<Unit> {
        articleStatusDao.toggleFavoriteStatusAtomic(articleId)
        return articleRepository.getOrLoadFullArticleById(articleId).map { }
    }

    override suspend fun toggleReadStatus(articleId: String) {
        articleStatusDao.toggleReadStatusAtomic(articleId)
    }
}