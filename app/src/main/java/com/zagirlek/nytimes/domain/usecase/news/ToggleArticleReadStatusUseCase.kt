package com.zagirlek.nytimes.domain.usecase.news

fun interface ToggleArticleReadStatusUseCase {
    suspend operator fun invoke(
        articleId: String
    )
}