package com.zagirlek.nytimes.data.usecase.news

import com.zagirlek.common.model.NewsCategory
import com.zagirlek.news.model.ArticleLiteWithStatus
import com.zagirlek.news.repository.NewsRepository
import com.zagirlek.news.usecase.FavoriteNewsFlowUseCase
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