package com.zagirlek.nytimes.domain.usecase.news

import androidx.paging.PagingData
import com.zagirlek.common.model.NewsCategory
import com.zagirlek.nytimes.domain.model.ArticleLiteWithStatus
import kotlinx.coroutines.flow.Flow

fun interface LatestNewsPagingUseCase {
    operator fun invoke(
        category: NewsCategory?,
        titleQuery: String?
    ): Flow<PagingData<ArticleLiteWithStatus>>
}