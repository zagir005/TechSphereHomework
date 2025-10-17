package com.zagirlek.nytimes.data.usecase.di

import com.zagirlek.news.usecase.FavoriteNewsFlowUseCase
import com.zagirlek.news.usecase.GetArticleFullFlowUseCase
import com.zagirlek.news.usecase.LatestNewsPagingUseCase
import com.zagirlek.news.usecase.ToggleArticleFavoriteStatusUseCase
import com.zagirlek.news.usecase.ToggleArticleReadStatusUseCase
import com.zagirlek.nytimes.data.repository.di.RepositoryModule
import com.zagirlek.nytimes.data.usecase.news.FavoriteNewsFlowUseCaseImpl
import com.zagirlek.nytimes.data.usecase.news.GetArticleFullFlowUseCaseImpl
import com.zagirlek.nytimes.data.usecase.news.LatestNewsPagingUseCaseImpl
import com.zagirlek.nytimes.data.usecase.news.ToggleArticleFavoriteStatusUseCaseImpl
import com.zagirlek.nytimes.data.usecase.news.ToggleArticleReadStatusUseCaseImpl
import com.zagirlek.weather.usecase.AddWeatherPointUseCase
import com.zagirlek.weather.usecase.DeleteWeatherPointUseCase
import com.zagirlek.weather.usecase.GetCityAutocompleteUseCase
import com.zagirlek.weather.usecase.GetOrPutCityUseCase
import com.zagirlek.weather.usecase.GetRecentCityListUseCase
import com.zagirlek.weather.usecase.GetWeatherPointsHistoryFlowUseCase
import com.zagirlek.nytimes.domain.UseCaseModule

class UseCaseModuleImpl(
    private val repositoryModule: RepositoryModule
): UseCaseModule {

    override fun getLatestNewsPagingUseCase(): LatestNewsPagingUseCase =
        LatestNewsPagingUseCaseImpl(
            newsRepository = repositoryModule.newsRepository
        )

    override fun getFavoriteNewsFlowUseCase(): FavoriteNewsFlowUseCase =
        FavoriteNewsFlowUseCaseImpl(
            newsRepository = repositoryModule.newsRepository
        )

    override fun toggleArticleFavoriteStatusUseCase(): ToggleArticleFavoriteStatusUseCase =
        ToggleArticleFavoriteStatusUseCaseImpl(
            articleStatusRepository = repositoryModule.articleStatusRepository
        )

    override fun toggleArticleReadStatusUseCase(): ToggleArticleReadStatusUseCase =
        ToggleArticleReadStatusUseCaseImpl(
            articleStatusRepository = repositoryModule.articleStatusRepository
        )

    override fun getArticleFullByIdFlowUseCase(): GetArticleFullFlowUseCase =
        GetArticleFullFlowUseCaseImpl(
            articleRepository = repositoryModule.articleRepository
        )
}