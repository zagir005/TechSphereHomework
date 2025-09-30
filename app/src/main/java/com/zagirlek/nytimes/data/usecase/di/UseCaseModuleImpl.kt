package com.zagirlek.nytimes.data.usecase.di

import com.zagirlek.nytimes.data.repository.di.RepositoryModule
import com.zagirlek.nytimes.data.usecase.AddWeatherPointUseCaseImpl
import com.zagirlek.nytimes.data.usecase.AuthUseCaseImpl
import com.zagirlek.nytimes.data.usecase.AuthWithoutLoginUseCaseImpl
import com.zagirlek.nytimes.data.usecase.DeleteWeatherPointUseCaseImpl
import com.zagirlek.nytimes.data.usecase.GetArticleByIdUseCaseImpl
import com.zagirlek.nytimes.data.usecase.GetCityAutocompleteUseCaseImpl
import com.zagirlek.nytimes.data.usecase.GetCurrentAuthTokenUseCaseImpl
import com.zagirlek.nytimes.data.usecase.GetOrPutCityUseCaseImpl
import com.zagirlek.nytimes.data.usecase.GetRecentCityListUseCaseImpl
import com.zagirlek.nytimes.data.usecase.GetWeatherPointsHistoryFlowUseCaseImpl
import com.zagirlek.nytimes.domain.usecase.AddWeatherPointUseCase
import com.zagirlek.nytimes.domain.usecase.AuthUseCase
import com.zagirlek.nytimes.domain.usecase.AuthWithoutLoginUseCase
import com.zagirlek.nytimes.domain.usecase.DeleteWeatherPointUseCase
import com.zagirlek.nytimes.domain.usecase.GetArticleByIdUseCase
import com.zagirlek.nytimes.domain.usecase.GetCityAutocompleteUseCase
import com.zagirlek.nytimes.domain.usecase.GetCurrentAuthTokenUseCase
import com.zagirlek.nytimes.domain.usecase.GetOrPutCityUseCase
import com.zagirlek.nytimes.domain.usecase.GetRecentCityListUseCase
import com.zagirlek.nytimes.domain.usecase.GetWeatherPointsHistoryFlowUseCase
import com.zagirlek.nytimes.domain.usecase.di.UseCaseModule

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



    override fun getArticleByIdUseCase(): GetArticleByIdUseCase =
        GetArticleByIdUseCaseImpl(
            newsRepository = repositoryModule.newsRepository
        )
}