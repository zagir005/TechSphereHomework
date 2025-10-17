package com.zagirlek.nytimes.data.usecase.news

import com.zagirlek.news.repository.ArticleStatusRepository
import com.zagirlek.news.usecase.ToggleArticleFavoriteStatusUseCase

class ToggleArticleFavoriteStatusUseCaseImpl(
    private val articleStatusRepository: ArticleStatusRepository
): ToggleArticleFavoriteStatusUseCase {
    override suspend fun invoke(articleId: String) =
        articleStatusRepository.toggleFavoriteStatus(articleId)
}