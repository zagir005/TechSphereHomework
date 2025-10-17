package com.zagirlek.news.usecase

import androidx.paging.PagingData
import com.zagirlek.common.model.NewsCategory
import com.zagirlek.news.model.ArticleLiteWithStatus
import kotlinx.coroutines.flow.Flow

fun interface LatestNewsPagingUseCase {
    operator fun invoke(
        category: NewsCategory?,
        titleQuery: String?
    ): Flow<PagingData<ArticleLiteWithStatus>>
}