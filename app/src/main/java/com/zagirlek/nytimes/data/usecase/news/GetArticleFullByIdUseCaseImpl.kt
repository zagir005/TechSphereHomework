package com.zagirlek.nytimes.data.usecase.news

import com.zagirlek.nytimes.domain.model.ArticleFullWithStatus
import com.zagirlek.nytimes.domain.repository.ArticleRepository
import com.zagirlek.nytimes.domain.usecase.news.GetArticleFullByIdUseCase

class GetArticleFullByIdUseCaseImpl(
    private val articleRepository: ArticleRepository
): GetArticleFullByIdUseCase {
    override suspend fun invoke(articleId: String): Result<ArticleFullWithStatus> =
        articleRepository.getFullArticleById(articleId = articleId)
}