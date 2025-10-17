package com.zagirlek.news.usecase

fun interface ToggleArticleReadStatusUseCase {
    suspend operator fun invoke(
        articleId: String
    )
}