package com.zagirlek.news.usecase

fun interface ToggleArticleFavoriteStatusUseCase {
    suspend operator fun invoke(
        articleId: String
    ): Result<Unit>
}