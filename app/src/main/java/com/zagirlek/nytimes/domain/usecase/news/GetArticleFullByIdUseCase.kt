package com.zagirlek.nytimes.domain.usecase.news

import com.zagirlek.nytimes.domain.model.ArticleFullWithStatus

fun interface GetArticleFullByIdUseCase {
    suspend operator fun invoke(
        articleId: String
    ): Result<ArticleFullWithStatus>
}