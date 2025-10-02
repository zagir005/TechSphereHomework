package com.zagirlek.nytimes.domain.usecase

fun interface UpdateArticleStatusUseCase {
    suspend operator fun invoke(
        articleId: String, isFavorite: Boolean, isRead: Boolean
    )
}