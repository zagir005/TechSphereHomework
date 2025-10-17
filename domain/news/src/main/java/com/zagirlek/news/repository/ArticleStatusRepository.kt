package com.zagirlek.news.repository

interface ArticleStatusRepository {
    suspend fun toggleFavoriteStatus(
        articleId: String
    ): Result<Unit>
    suspend fun toggleReadStatus(
        articleId: String
    )
}