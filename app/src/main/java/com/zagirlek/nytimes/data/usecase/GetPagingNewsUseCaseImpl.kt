package com.zagirlek.nytimes.data.usecase

import androidx.paging.PagingData
import com.zagirlek.nytimes.core.model.NewsFilter
import com.zagirlek.nytimes.domain.model.ArticleLite
import com.zagirlek.nytimes.domain.repository.NewsRepository
import com.zagirlek.nytimes.domain.usecase.GetPagingNewsUseCase
import kotlinx.coroutines.flow.Flow

class GetPagingNewsUseCaseImpl(
    private val newsRepository: NewsRepository
): GetPagingNewsUseCase {
    override fun invoke(filter: NewsFilter): Flow<PagingData<ArticleLite>> =
        newsRepository.getPagedNews(filter)
}