package com.zagirlek.nytimes.data.usecase.news

import com.zagirlek.nytimes.domain.repository.ArticleStatusRepository
import com.zagirlek.nytimes.domain.usecase.news.ToggleArticleFavoriteStatusUseCase

class ToggleArticleFavoriteStatusUseCaseImpl(
    private val articleStatusRepository: ArticleStatusRepository
): ToggleArticleFavoriteStatusUseCase {
    override suspend fun invoke(articleId: String) {
        articleStatusRepository.toggleFavoriteStatus(articleId)
    }
}