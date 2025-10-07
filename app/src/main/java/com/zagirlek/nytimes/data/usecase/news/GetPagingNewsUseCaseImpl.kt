package com.zagirlek.nytimes.data.usecase.news

import androidx.paging.PagingData
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.domain.model.ArticleLiteWithStatus
import com.zagirlek.nytimes.domain.repository.NewsRepository
import com.zagirlek.nytimes.domain.usecase.news.GetPagingNewsUseCase
import kotlinx.coroutines.flow.Flow

class GetPagingNewsUseCaseImpl(
    private val newsRepository: NewsRepository
): GetPagingNewsUseCase {
    override fun invoke(
        category: NewsCategory?,
        titleQuery: String?
    ): Flow<PagingData<ArticleLiteWithStatus>> =
        newsRepository.getNewsPager(
            category = category,
            titleQuery = titleQuery
        )
}