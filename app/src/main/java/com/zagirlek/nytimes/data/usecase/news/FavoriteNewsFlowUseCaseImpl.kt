package com.zagirlek.nytimes.data.usecase.news

import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.domain.model.ArticleLiteWithStatus
import com.zagirlek.nytimes.domain.repository.NewsRepository
import com.zagirlek.nytimes.domain.usecase.news.FavoriteNewsFlowUseCase
import kotlinx.coroutines.flow.Flow

class FavoriteNewsFlowUseCaseImpl(
    private val newsRepository: NewsRepository
): FavoriteNewsFlowUseCase {
    override fun invoke(
        category: NewsCategory?,
        titleQuery: String?
    ): Flow<List<ArticleLiteWithStatus>> =
        newsRepository.getFavoriteNewsFlow(
            category = category,
            titleQuery = titleQuery
        )
}