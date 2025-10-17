package com.zagirlek.news.usecase

import com.zagirlek.news.repository.ArticleStatusRepository

class ToggleArticleReadStatusUseCase(
    private val articleStatusRepository: ArticleStatusRepository
) {
    suspend operator fun invoke(articleId: String) {
        articleStatusRepository.toggleReadStatus(articleId)
    }
}