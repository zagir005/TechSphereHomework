package com.zagirlek.news.usecase

import androidx.paging.PagingData
import com.zagirlek.common.model.NewsCategory
import com.zagirlek.news.model.ArticleLiteWithStatus
import com.zagirlek.news.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class LatestNewsPagingUseCase(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(
        category: NewsCategory?,
        titleQuery: String?
    ): Flow<PagingData<ArticleLiteWithStatus>> =
        newsRepository.getNewsPager(
            category = category,
            titleQuery = titleQuery
        )
}