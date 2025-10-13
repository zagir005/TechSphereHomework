package com.zagirlek.nytimes.data.usecase.news

import com.zagirlek.nytimes.domain.model.ArticleFullWithStatus
import com.zagirlek.nytimes.domain.repository.ArticleRepository
import com.zagirlek.nytimes.domain.usecase.news.GetArticleFullByIdFlowUseCase
import kotlinx.coroutines.flow.Flow

class GetArticleFullByIdFlowUseCaseImpl(
    private val articleRepository: ArticleRepository
): GetArticleFullByIdFlowUseCase {
    override suspend fun invoke(articleId: String): Flow<Result<ArticleFullWithStatus>> =
        articleRepository.getOrLoadFullArticleByIdFlow(articleId)
}