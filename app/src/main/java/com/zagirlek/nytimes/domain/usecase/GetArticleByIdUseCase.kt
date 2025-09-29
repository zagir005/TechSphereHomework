package com.zagirlek.nytimes.domain.usecase

import com.zagirlek.nytimes.domain.model.ArticleFull

fun interface GetArticleByIdUseCase {
    suspend operator fun invoke(
        articleId: String
    ): Result<ArticleFull>
}