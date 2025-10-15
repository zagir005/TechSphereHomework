package com.zagirlek.nytimes.domain.usecase.news

import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.domain.model.ArticleFullWithStatus
import kotlinx.coroutines.flow.Flow

fun interface FavoriteNewsFlowUseCase {
    operator fun invoke(
        category: NewsCategory?,
        titleQuery: String?
    ): Flow<List<ArticleFullWithStatus>>
}