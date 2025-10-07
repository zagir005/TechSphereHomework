package com.zagirlek.nytimes.domain.repository

import com.zagirlek.nytimes.domain.model.ArticleFull
import com.zagirlek.nytimes.domain.model.ArticleFullWithStatus

interface ArticleRepository {
    suspend fun getFullArticleById(
        articleId: String
    ): Result<ArticleFullWithStatus>
}