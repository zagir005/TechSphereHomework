package com.zagirlek.news.usecase

import com.zagirlek.news.model.ArticleFullWithStatus
import kotlinx.coroutines.flow.Flow

fun interface GetArticleFullFlowUseCase {
    suspend operator fun invoke(
        articleId: String
    ): Flow<Result<ArticleFullWithStatus>>
}