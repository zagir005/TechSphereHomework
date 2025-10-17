package com.zagirlek.nytimes.data.usecase.news

import com.zagirlek.news.model.ArticleFullWithStatus
import com.zagirlek.news.repository.ArticleRepository
import com.zagirlek.news.usecase.GetArticleFullFlowUseCase
import kotlinx.coroutines.flow.Flow

class GetArticleFullFlowUseCaseImpl(
    private val articleRepository: ArticleRepository
): GetArticleFullFlowUseCase {
    override suspend fun invoke(articleId: String): Flow<Result<ArticleFullWithStatus>> =
        articleRepository.getOrLoadFullArticleByIdFlow(articleId)
}