package com.zagirlek.nytimes.data.usecase

import com.zagirlek.nytimes.domain.model.NewsFilter
import com.zagirlek.nytimes.domain.model.NewsPage
import com.zagirlek.nytimes.domain.repository.NewsRepository
import com.zagirlek.nytimes.domain.usecase.GetLatestNewsUseCase

class GetLatestNewsUseCaseImpl(
    private val newsRepository: NewsRepository
): GetLatestNewsUseCase {
    override suspend fun invoke(newsFilter: NewsFilter): Result<NewsPage> =
        newsRepository.getLatestNews(
            category = newsFilter.category,
            page = newsFilter.page,
            titleQuery = newsFilter.titleQuery
        )
}