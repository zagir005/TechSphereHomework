package com.zagirlek.news.di

import com.zagirlek.news.repository.ArticleRepository
import com.zagirlek.news.repository.ArticleStatusRepository
import com.zagirlek.news.repository.NewsRepository
import com.zagirlek.news.usecase.FavoriteNewsFlowUseCase
import com.zagirlek.news.usecase.GetArticleFullFlowUseCase
import com.zagirlek.news.usecase.LatestNewsPagingUseCase
import com.zagirlek.news.usecase.ToggleArticleFavoriteStatusUseCase
import com.zagirlek.news.usecase.ToggleArticleReadStatusUseCase

class NewsDomainModule(
    private val newsRepository: NewsRepository,
    private val articleRepository: ArticleRepository,
    private val articleStatusRepository: ArticleStatusRepository
) {
    fun provideFavoriteNewsFlowUseCase(): FavoriteNewsFlowUseCase =
        FavoriteNewsFlowUseCase(newsRepository)

    fun provideGetArticleFullFlowUseCase(): GetArticleFullFlowUseCase =
        GetArticleFullFlowUseCase(articleRepository)

    fun provideLatestNewsPagingUseCase(): LatestNewsPagingUseCase =
        LatestNewsPagingUseCase(newsRepository)

    fun provideToggleArticleFavoriteStatusUseCase(): ToggleArticleFavoriteStatusUseCase =
        ToggleArticleFavoriteStatusUseCase(articleStatusRepository)

    fun provideToggleArticleReadStatusUseCase(): ToggleArticleReadStatusUseCase =
        ToggleArticleReadStatusUseCase(articleStatusRepository)
}