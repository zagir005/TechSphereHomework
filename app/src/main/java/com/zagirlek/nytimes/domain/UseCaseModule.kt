package com.zagirlek.nytimes.domain

import com.zagirlek.news.usecase.FavoriteNewsFlowUseCase
import com.zagirlek.news.usecase.GetArticleFullFlowUseCase
import com.zagirlek.news.usecase.LatestNewsPagingUseCase
import com.zagirlek.news.usecase.ToggleArticleFavoriteStatusUseCase
import com.zagirlek.news.usecase.ToggleArticleReadStatusUseCase

interface UseCaseModule {
    fun getLatestNewsPagingUseCase(): LatestNewsPagingUseCase
    fun getFavoriteNewsFlowUseCase(): FavoriteNewsFlowUseCase

    fun toggleArticleFavoriteStatusUseCase(): ToggleArticleFavoriteStatusUseCase
    fun toggleArticleReadStatusUseCase(): ToggleArticleReadStatusUseCase
    fun getArticleFullByIdFlowUseCase(): GetArticleFullFlowUseCase
}