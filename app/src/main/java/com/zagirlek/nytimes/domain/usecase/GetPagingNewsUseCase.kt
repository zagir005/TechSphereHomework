package com.zagirlek.nytimes.domain.usecase

import androidx.paging.PagingData
import com.zagirlek.nytimes.core.model.NewsFilter
import com.zagirlek.nytimes.domain.model.ArticleLite
import kotlinx.coroutines.flow.Flow

fun interface GetPagingNewsUseCase {
    operator fun invoke(filter: NewsFilter): Flow<PagingData<ArticleLite>>
}