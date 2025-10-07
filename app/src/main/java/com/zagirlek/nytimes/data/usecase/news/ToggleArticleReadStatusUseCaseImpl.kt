package com.zagirlek.nytimes.data.usecase.news

import com.zagirlek.nytimes.domain.repository.ArticleStatusRepository
import com.zagirlek.nytimes.domain.usecase.news.ToggleArticleReadStatusUseCase

class ToggleArticleReadStatusUseCaseImpl(
    private val articleStatusRepository: ArticleStatusRepository
): ToggleArticleReadStatusUseCase {
    override suspend fun invoke(articleId: String) {
        articleStatusRepository.toggleReadStatus(articleId)
    }
}