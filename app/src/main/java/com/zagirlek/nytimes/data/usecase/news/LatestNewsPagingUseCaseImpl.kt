package com.zagirlek.nytimes.data.usecase.news

import androidx.paging.PagingData
import com.zagirlek.common.model.NewsCategory
import com.zagirlek.news.model.ArticleLiteWithStatus
import com.zagirlek.news.repository.NewsRepository
import com.zagirlek.news.usecase.LatestNewsPagingUseCase
import kotlinx.coroutines.flow.Flow

class LatestNewsPagingUseCaseImpl(
    private val newsRepository: NewsRepository
): LatestNewsPagingUseCase {
    override fun invoke(
        category: NewsCategory?,
        titleQuery: String?
    ): Flow<PagingData<ArticleLiteWithStatus>> =
        newsRepository.getNewsPager(
            category = category,
            titleQuery = titleQuery
        )
}