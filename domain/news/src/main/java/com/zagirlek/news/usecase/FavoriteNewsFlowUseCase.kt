package com.zagirlek.news.usecase

import com.zagirlek.common.model.NewsCategory
import com.zagirlek.news.model.ArticleLiteWithStatus
import com.zagirlek.news.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class FavoriteNewsFlowUseCase(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(
        category: NewsCategory?,
        titleQuery: String?
    ): Flow<List<ArticleLiteWithStatus>> =
        newsRepository.getFavoriteNewsFlow(
            category = category,
            titleQuery = titleQuery
        )
}