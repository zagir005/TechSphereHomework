package com.zagirlek.nytimes.data.usecase.di

import com.zagirlek.nytimes.data.repository.di.RepositoryModule
import com.zagirlek.nytimes.data.usecase.AddWeatherPointUseCaseImpl
import com.zagirlek.nytimes.data.usecase.DeleteWeatherPointUseCaseImpl
import com.zagirlek.nytimes.data.usecase.GetCityAutocompleteUseCaseImpl
import com.zagirlek.nytimes.data.usecase.GetOrPutCityUseCaseImpl
import com.zagirlek.nytimes.data.usecase.GetRecentCityListUseCaseImpl
import com.zagirlek.nytimes.data.usecase.GetWeatherPointsHistoryFlowUseCaseImpl
import com.zagirlek.nytimes.domain.usecase.AddWeatherPointUseCase
import com.zagirlek.nytimes.domain.usecase.DeleteWeatherPointUseCase
import com.zagirlek.nytimes.domain.usecase.GetCityAutocompleteUseCase
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

    override fun getOrSaveCityUseCase(): GetOrPutCityUseCase =
        GetOrPutCityUseCaseImpl(
            cityRepository = repositoryModule.getCityRepository()
        )

    override fun getRecentCityListUseCase(): GetRecentCityListUseCase =
        GetRecentCityListUseCaseImpl(
            cityRepository = repositoryModule.getCityRepository()
        )
}