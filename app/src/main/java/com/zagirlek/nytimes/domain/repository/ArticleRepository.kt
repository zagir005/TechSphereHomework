package com.zagirlek.nytimes.domain.repository

import com.zagirlek.nytimes.domain.model.ArticleFullWithStatus
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    suspend fun getOrLoadFullArticleById(
        articleId: String
    ): Result<ArticleFullWithStatus>

    suspend fun getOrLoadFullArticleByIdFlow(
        articleId: String
    ): Flow<Result<ArticleFullWithStatus?>>
}