package com.zagirlek.nytimes.data.usecase

import com.zagirlek.nytimes.domain.repository.NewsRepository
import com.zagirlek.nytimes.domain.usecase.UpdateArticleStatusUseCase

class UpdateArticleStatusUseCaseImpl(
    private val newsRepository: NewsRepository
): UpdateArticleStatusUseCase {
    override suspend fun invoke(articleId: String, isFavorite: Boolean, isRead: Boolean) =
        newsRepository.updateArticleStatus(
            articleId = articleId,
            isFavorite = isFavorite,
            isRead = isRead
        )
}