package com.zagirlek.news.repository


import com.zagirlek.news.model.ArticleFullWithStatus
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    suspend fun getOrLoadFullArticleById(
        articleId: String
    ): Result<ArticleFullWithStatus>

    suspend fun getOrLoadFullArticleByIdFlow(
        articleId: String
    ): Flow<Result<ArticleFullWithStatus>>
}