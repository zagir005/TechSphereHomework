package com.zagirlek.nytimes.domain.usecase.news

import com.zagirlek.nytimes.domain.model.ArticleFullWithStatus
import kotlinx.coroutines.flow.Flow

fun interface GetArticleFullByIdFlowUseCase {
    suspend operator fun invoke(
        articleId: String
    ): Flow<Result<ArticleFullWithStatus?>>
}