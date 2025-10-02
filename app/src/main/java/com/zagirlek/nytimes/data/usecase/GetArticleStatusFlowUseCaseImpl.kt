package com.zagirlek.nytimes.data.usecase

import com.zagirlek.nytimes.domain.model.ArticleStatus
import com.zagirlek.nytimes.domain.repository.NewsRepository
import com.zagirlek.nytimes.domain.usecase.GetArticleStatusFlowUseCase
import kotlinx.coroutines.flow.Flow

class GetArticleStatusFlowUseCaseImpl(
    private val newsRepository: NewsRepository
): GetArticleStatusFlowUseCase {
    override fun invoke(): Flow<List<ArticleStatus>> =
        newsRepository.getArticleListStatusFlow()
}