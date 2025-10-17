package com.zagirlek.news.usecase

import com.zagirlek.news.model.ArticleFullWithStatus
import com.zagirlek.news.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow

class GetArticleFullFlowUseCase(
    private val articleRepository: ArticleRepository
) {
    suspend operator fun invoke(articleId: String): Flow<Result<ArticleFullWithStatus>> =
        articleRepository.getOrLoadFullArticleByIdFlow(articleId)
}