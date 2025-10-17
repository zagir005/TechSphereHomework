package com.zagirlek.news.usecase

import com.zagirlek.news.repository.ArticleStatusRepository

class ToggleArticleFavoriteStatusUseCase(
    private val articleStatusRepository: ArticleStatusRepository
) {
    suspend operator fun invoke(articleId: String) =
        articleStatusRepository.toggleFavoriteStatus(articleId)
}