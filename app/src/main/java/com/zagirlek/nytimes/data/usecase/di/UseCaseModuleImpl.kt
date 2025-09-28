package com.zagirlek.nytimes.data.usecase.di

import com.zagirlek.nytimes.data.repository.di.RepositoryModule
import com.zagirlek.nytimes.data.usecase.AddWeatherPointUseCaseImpl
import com.zagirlek.nytimes.data.usecase.AuthUseCaseImpl
import com.zagirlek.nytimes.data.usecase.AuthWithoutLoginUseCaseImpl
import com.zagirlek.nytimes.data.usecase.DeleteWeatherPointUseCaseImpl
import com.zagirlek.nytimes.data.usecase.GetCityAutocompleteUseCaseImpl
import com.zagirlek.nytimes.data.usecase.GetCurrentAuthTokenUseCaseImpl
import com.zagirlek.nytimes.data.usecase.GetOrPutCityUseCaseImpl
import com.zagirlek.nytimes.data.usecase.GetRecentCityListUseCaseImpl
import com.zagirlek.nytimes.data.usecase.GetWeatherPointsHistoryFlowUseCaseImpl
import com.zagirlek.nytimes.domain.usecase.AddWeatherPointUseCase
import com.zagirlek.nytimes.domain.usecase.AuthUseCase
import com.zagirlek.nytimes.domain.usecase.AuthWithoutLoginUseCase
import com.zagirlek.nytimes.domain.usecase.DeleteWeatherPointUseCase
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
            weatherRepository = repositoryModule.getWeatherRepository()
        )

    override fun deleteWeatherPointUseCase(): DeleteWeatherPointUseCase =
        DeleteWeatherPointUseCaseImpl(
            weatherRepository = repositoryModule.getWeatherRepository()
        )

    override fun getWeatherPointsHistoryFlowUseCase(): GetWeatherPointsHistoryFlowUseCase =
        GetWeatherPointsHistoryFlowUseCaseImpl(
            weatherRepository = repositoryModule.getWeatherRepository()
        )

    override fun getCityAutocompleteUseCase(): GetCityAutocompleteUseCase =
        GetCityAutocompleteUseCaseImpl(
            autocompleteRepository = repositoryModule.getCityAutocompleteRepository()
        )

    override fun getRecentCityListUseCase(): GetRecentCityListUseCase =
        GetRecentCityListUseCaseImpl(
            cityRepository = repositoryModule.getCityRepository()
        )

    override fun getOrPutCityUseCase(): GetOrPutCityUseCase =
        GetOrPutCityUseCaseImpl(
            cityRepository = repositoryModule.getCityRepository()
        )

    override fun authUseCase(): AuthUseCase =
        AuthUseCaseImpl(
            authRepository = repositoryModule.getAuthRepository()
        )

    override fun authWithoutLoginUseCase(): AuthWithoutLoginUseCase =
        AuthWithoutLoginUseCaseImpl(
            authRepository = repositoryModule.getAuthRepository()
        )

    override fun getCurrentAuthTokenUseCase(): GetCurrentAuthTokenUseCase =
        GetCurrentAuthTokenUseCaseImpl(
            authRepository = repositoryModule.getAuthRepository()
        )
}