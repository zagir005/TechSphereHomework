package com.zagirlek.nytimes.data.usecase.di

import com.zagirlek.auth.usecase.AuthUseCase
import com.zagirlek.auth.usecase.AuthWithoutLoginUseCase
import com.zagirlek.auth.usecase.GetCurrentAuthTokenUseCase
import com.zagirlek.news.usecase.FavoriteNewsFlowUseCase
import com.zagirlek.news.usecase.GetArticleFullFlowUseCase
import com.zagirlek.news.usecase.LatestNewsPagingUseCase
import com.zagirlek.news.usecase.ToggleArticleFavoriteStatusUseCase
import com.zagirlek.news.usecase.ToggleArticleReadStatusUseCase
import com.zagirlek.nytimes.data.repository.di.RepositoryModule
import com.zagirlek.nytimes.data.usecase.auth.AuthUseCaseImpl
import com.zagirlek.nytimes.data.usecase.auth.AuthWithoutLoginUseCaseImpl
import com.zagirlek.nytimes.data.usecase.auth.GetCurrentAuthTokenUseCaseImpl
import com.zagirlek.nytimes.data.usecase.news.FavoriteNewsFlowUseCaseImpl
import com.zagirlek.nytimes.data.usecase.news.GetArticleFullFlowUseCaseImpl
import com.zagirlek.nytimes.data.usecase.news.LatestNewsPagingUseCaseImpl
import com.zagirlek.nytimes.data.usecase.news.ToggleArticleFavoriteStatusUseCaseImpl
import com.zagirlek.nytimes.data.usecase.news.ToggleArticleReadStatusUseCaseImpl
import com.zagirlek.nytimes.data.usecase.weather.AddWeatherPointUseCaseImpl
import com.zagirlek.nytimes.data.usecase.weather.DeleteWeatherPointUseCaseImpl
import com.zagirlek.nytimes.data.usecase.weather.GetCityAutocompleteUseCaseImpl
import com.zagirlek.nytimes.data.usecase.weather.GetOrPutCityUseCaseImpl
import com.zagirlek.nytimes.data.usecase.weather.GetRecentCityListUseCaseImpl
import com.zagirlek.nytimes.data.usecase.weather.GetWeatherPointsHistoryFlowUseCaseImpl
import com.zagirlek.nytimes.domain.UseCaseModule
import com.zagirlek.weather.usecase.AddWeatherPointUseCase
import com.zagirlek.weather.usecase.DeleteWeatherPointUseCase
import com.zagirlek.weather.usecase.GetCityAutocompleteUseCase
import com.zagirlek.weather.usecase.GetOrPutCityUseCase
import com.zagirlek.weather.usecase.GetRecentCityListUseCase
import com.zagirlek.weather.usecase.GetWeatherPointsHistoryFlowUseCase

class UseCaseModuleImpl(
    private val repositoryModule: RepositoryModule
): UseCaseModule {
    override fun addWeatherPointUseCase(): AddWeatherPointUseCase =
        AddWeatherPointUseCaseImpl(
            weatherRepository = repositoryModule.weatherRepository
        )

    override fun deleteWeatherPointUseCase(): DeleteWeatherPointUseCase =
        DeleteWeatherPointUseCaseImpl(
            weatherRepository = repositoryModule.weatherRepository
        )

    override fun getWeatherPointsHistoryFlowUseCase(): GetWeatherPointsHistoryFlowUseCase =
        GetWeatherPointsHistoryFlowUseCaseImpl(
            weatherRepository = repositoryModule.weatherRepository
        )

    override fun getCityAutocompleteUseCase(): GetCityAutocompleteUseCase =
        GetCityAutocompleteUseCaseImpl(
            autocompleteRepository = repositoryModule.cityAutocompleteRepository
        )

    override fun getRecentCityListUseCase(): GetRecentCityListUseCase =
        GetRecentCityListUseCaseImpl(
            cityRepository = repositoryModule.cityRepository
        )

    override fun getOrPutCityUseCase(): GetOrPutCityUseCase =
        GetOrPutCityUseCaseImpl(
            cityRepository = repositoryModule.cityRepository
        )

    override fun authUseCase(): AuthUseCase =
        AuthUseCaseImpl(
            authRepository = repositoryModule.authRepository
        )

    override fun authWithoutLoginUseCase(): AuthWithoutLoginUseCase =
        AuthWithoutLoginUseCaseImpl(
            authRepository = repositoryModule.authRepository
        )

    override fun getCurrentAuthTokenUseCase(): GetCurrentAuthTokenUseCase =
        GetCurrentAuthTokenUseCaseImpl(
            authRepository = repositoryModule.authRepository
        )

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