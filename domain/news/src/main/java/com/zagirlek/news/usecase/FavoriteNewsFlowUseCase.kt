package com.zagirlek.news.usecase

import com.zagirlek.common.model.NewsCategory
import com.zagirlek.news.model.ArticleLiteWithStatus
import kotlinx.coroutines.flow.Flow

fun interface FavoriteNewsFlowUseCase {
    operator fun invoke(
        category: NewsCategory?,
        titleQuery: String?
    ): Flow<List<ArticleLiteWithStatus>>
}