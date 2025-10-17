package com.zagirlek.nytimes.data.usecase.news

import com.zagirlek.news.repository.ArticleStatusRepository
import com.zagirlek.news.usecase.ToggleArticleReadStatusUseCase

class ToggleArticleReadStatusUseCaseImpl(
    private val articleStatusRepository: ArticleStatusRepository
): ToggleArticleReadStatusUseCase {
    override suspend fun invoke(articleId: String) {
        articleStatusRepository.toggleReadStatus(articleId)
    }
}