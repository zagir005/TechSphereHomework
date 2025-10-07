package com.zagirlek.nytimes.domain.repository

import com.zagirlek.nytimes.domain.model.ArticleStatus

interface ArticleStatusRepository {
    suspend fun getOrCreateArticleStatusById(articleId: String): ArticleStatus
    suspend fun toggleFavoriteStatus(
        articleId: String
    )
    suspend fun toggleReadStatus(
        articleId: String
    )
}