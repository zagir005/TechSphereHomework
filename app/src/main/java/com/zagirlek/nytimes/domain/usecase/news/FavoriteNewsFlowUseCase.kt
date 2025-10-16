package com.zagirlek.nytimes.domain.usecase.news

import com.zagirlek.common.model.NewsCategory
import com.zagirlek.nytimes.domain.model.ArticleLiteWithStatus
import kotlinx.coroutines.flow.Flow

fun interface FavoriteNewsFlowUseCase {
    operator fun invoke(
        category: NewsCategory?,
        titleQuery: String?
    ): Flow<List<ArticleLiteWithStatus>>
}