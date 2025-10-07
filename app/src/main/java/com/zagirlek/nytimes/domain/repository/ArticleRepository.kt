package com.zagirlek.nytimes.domain.repository

import com.zagirlek.nytimes.domain.model.ArticleFullWithStatus

interface ArticleRepository {
    suspend fun getOrLoadFullArticleById(
        articleId: String
    ): Result<ArticleFullWithStatus>
}