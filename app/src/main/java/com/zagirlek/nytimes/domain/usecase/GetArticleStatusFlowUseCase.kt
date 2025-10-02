package com.zagirlek.nytimes.domain.usecase

import com.zagirlek.nytimes.domain.model.ArticleStatus
import kotlinx.coroutines.flow.Flow

fun interface GetArticleStatusFlowUseCase {
    operator fun invoke(): Flow<List<ArticleStatus>>
}