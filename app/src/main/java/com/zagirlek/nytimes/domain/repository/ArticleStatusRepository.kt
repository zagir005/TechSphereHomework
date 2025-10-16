package com.zagirlek.nytimes.domain.repository

interface ArticleStatusRepository {
    suspend fun toggleFavoriteStatus(
        articleId: String
    ): Result<Unit>
    suspend fun toggleReadStatus(
        articleId: String
    )
}