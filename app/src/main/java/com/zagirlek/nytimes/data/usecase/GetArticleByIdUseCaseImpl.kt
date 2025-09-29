package com.zagirlek.nytimes.data.usecase

import com.zagirlek.nytimes.domain.model.ArticleFull
import com.zagirlek.nytimes.domain.repository.NewsRepository
import com.zagirlek.nytimes.domain.usecase.GetArticleByIdUseCase

class GetArticleByIdUseCaseImpl(
    private val newsRepository: NewsRepository
): GetArticleByIdUseCase {
    override suspend fun invoke(articleId: String): Result<ArticleFull> =
        newsRepository.getFullArticleById(articleId)
}