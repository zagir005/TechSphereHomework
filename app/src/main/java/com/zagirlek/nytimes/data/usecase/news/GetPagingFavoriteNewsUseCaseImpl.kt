package com.zagirlek.nytimes.data.usecase.news

import androidx.paging.PagingData
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.domain.model.ArticleFullWithStatus
import com.zagirlek.nytimes.domain.repository.NewsRepository
import com.zagirlek.nytimes.domain.usecase.news.GetPagingFavoriteNewsUseCase
import kotlinx.coroutines.flow.Flow

class GetPagingFavoriteNewsUseCaseImpl(
    private val newsRepository: NewsRepository
): GetPagingFavoriteNewsUseCase {
    override fun invoke(
        category: NewsCategory?,
        titleQuery: String?
    ): Flow<PagingData<ArticleFullWithStatus>> =
        newsRepository.getFavoriteNewsPager(
            category = category,
            titleQuery = titleQuery
        )
}