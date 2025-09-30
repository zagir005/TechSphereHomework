package com.zagirlek.nytimes.domain.repository

import com.zagirlek.nytimes.domain.model.ArticleFull
import com.zagirlek.nytimes.domain.model.NewsCategory
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun isRead(articleId: String): Boolean
    suspend fun markRead(articleId: String): Result<Unit>
    suspend fun markUnread(articleId: String): Result<Unit>
    suspend fun addFavorite(article: ArticleFull): Result<Unit>
    suspend fun removeFavorite(articleId: String): Result<Unit>
    fun observeFavoritesByCategories(categories: List<NewsCategory> = emptyList()): Flow<List<ArticleFull>>
    fun observeAllCategories(): Flow<List<NewsCategory>>
}