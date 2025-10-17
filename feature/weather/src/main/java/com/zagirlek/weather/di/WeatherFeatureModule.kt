package com.zagirlek.weather.di

import com.arkivanov.decompose.ComponentContext
import com.zagirlek.weather.WeatherScreen
import com.zagirlek.weather.cmp.WeatherScreenComponent

class WeatherFeatureModule(
    private val weatherDomainModule: WeatherDomainModule
) {
    fun getWeatherComponent(
        componentContext: ComponentContext
    ): WeatherScreen =
        WeatherScreenComponent(
            componentContext = componentContext,
            getCityAutocompleteUseCase = weatherDomainModule.provideCityAutocompleteUseCase(),
            getWeatherPointsHistoryFlowUseCase = weatherDomainModule.provideGetWeatherPointsHistoryFlowUseCase(),
            getRecentCityListUseCase = weatherDomainModule.provideGetRecentCityListUseCase(),
            deleteWeatherPointUseCase = weatherDomainModule.provideDeleteWeatherPointUseCase(),
            addWeatherPointUseCase = weatherDomainModule.provideAddWeatherPointUseCase(),
            getOrPutCityUseCase = weatherDomainModule.provideGetOrPutCityUseCase()
        )
}