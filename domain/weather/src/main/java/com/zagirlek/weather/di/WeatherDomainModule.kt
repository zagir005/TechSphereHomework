package com.zagirlek.weather.di

import com.zagirlek.weather.repository.CityAutocompleteRepository
import com.zagirlek.weather.repository.CityRepository
import com.zagirlek.weather.repository.WeatherRepository
import com.zagirlek.weather.usecase.AddWeatherPointUseCase
import com.zagirlek.weather.usecase.DeleteWeatherPointUseCase
import com.zagirlek.weather.usecase.GetCityAutocompleteUseCase
import com.zagirlek.weather.usecase.GetOrPutCityUseCase
import com.zagirlek.weather.usecase.GetRecentCityListUseCase
import com.zagirlek.weather.usecase.GetWeatherPointsHistoryFlowUseCase

class WeatherDomainModule(
    private val cityAutocompleteRepository: CityAutocompleteRepository,
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository
) {
    fun provideAddWeatherPointUseCase(): AddWeatherPointUseCase =
        AddWeatherPointUseCase(weatherRepository)

    fun provideDeleteWeatherPointUseCase(): DeleteWeatherPointUseCase =
        DeleteWeatherPointUseCase(weatherRepository)

    fun provideCityAutocompleteUseCase(): GetCityAutocompleteUseCase =
        GetCityAutocompleteUseCase(cityAutocompleteRepository)

    fun provideGetOrPutCityUseCase(): GetOrPutCityUseCase =
        GetOrPutCityUseCase(cityRepository)

    fun provideGetRecentCityListUseCase(): GetRecentCityListUseCase =
        GetRecentCityListUseCase(cityRepository)

    fun provideGetWeatherPointsHistoryFlowUseCase(): GetWeatherPointsHistoryFlowUseCase =
        GetWeatherPointsHistoryFlowUseCase(weatherRepository)
}