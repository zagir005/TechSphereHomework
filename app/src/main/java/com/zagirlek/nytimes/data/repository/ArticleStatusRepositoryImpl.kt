package com.zagirlek.nytimes.data.repository

import com.zagirlek.nytimes.data.local.news.dao.ArticleStatusDao
import com.zagirlek.nytimes.domain.repository.ArticleRepository
import com.zagirlek.nytimes.domain.repository.ArticleStatusRepository

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