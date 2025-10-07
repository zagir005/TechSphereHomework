package com.zagirlek.nytimes.domain.usecase.news

import androidx.paging.PagingData
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.domain.model.ArticleFullWithStatus
import com.zagirlek.nytimes.domain.model.ArticleLiteWithStatus
import kotlinx.coroutines.flow.Flow

fun interface GetPagingFavoriteNewsUseCase {
    operator fun invoke(
        category: NewsCategory?,
        titleQuery: String?
    ): Flow<PagingData<ArticleFullWithStatus>>
}