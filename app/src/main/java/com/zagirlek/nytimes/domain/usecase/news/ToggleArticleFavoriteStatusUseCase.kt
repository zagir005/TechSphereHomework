package com.zagirlek.nytimes.domain.usecase.news

fun interface ToggleArticleFavoriteStatusUseCase {
    suspend operator fun invoke(
        articleId: String
    )
}