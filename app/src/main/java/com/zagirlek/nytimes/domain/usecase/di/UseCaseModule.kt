package com.zagirlek.nytimes.domain.usecase.di

import com.zagirlek.nytimes.domain.usecase.AddWeatherPointUseCase
import com.zagirlek.nytimes.domain.usecase.AuthUseCase
import com.zagirlek.nytimes.domain.usecase.AuthWithoutLoginUseCase
import com.zagirlek.nytimes.domain.usecase.DeleteWeatherPointUseCase
import com.zagirlek.nytimes.domain.usecase.GetCityAutocompleteUseCase
import com.zagirlek.nytimes.domain.usecase.GetCurrentAuthTokenUseCase
import com.zagirlek.nytimes.domain.usecase.GetOrPutCityUseCase
import com.zagirlek.nytimes.domain.usecase.GetRecentCityListUseCase
import com.zagirlek.nytimes.domain.usecase.GetWeatherPointsHistoryFlowUseCase

interface UseCaseModule {
    fun addWeatherPointUseCase(): AddWeatherPointUseCase
    fun deleteWeatherPointUseCase(): DeleteWeatherPointUseCase
    fun getWeatherPointsHistoryFlowUseCase(): GetWeatherPointsHistoryFlowUseCase
    fun getCityAutocompleteUseCase(): GetCityAutocompleteUseCase
    fun getRecentCityListUseCase(): GetRecentCityListUseCase
    fun getOrPutCityUseCase(): GetOrPutCityUseCase
    fun authUseCase(): AuthUseCase
    fun authWithoutLoginUseCase(): AuthWithoutLoginUseCase
    fun getCurrentAuthTokenUseCase(): GetCurrentAuthTokenUseCase
}