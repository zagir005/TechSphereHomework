package com.zagirlek.nytimes.data.repository.di

import com.zagirlek.nytimes.core.networkchecker.NetworkConnectionChecker
import com.zagirlek.nytimes.data.local.dao.CityDao
import com.zagirlek.nytimes.data.local.dao.WeatherDao
import com.zagirlek.nytimes.data.network.weather.service.AutocompleteService
import com.zagirlek.nytimes.data.repository.CityAutocompleteRepositoryImpl
import com.zagirlek.nytimes.data.repository.CityRepositoryImpl
import com.zagirlek.nytimes.data.repository.MockAuthRepositoryImpl
import com.zagirlek.nytimes.data.repository.WeatherRepositoryImpl
import com.zagirlek.nytimes.domain.repository.AuthRepository
import com.zagirlek.nytimes.domain.repository.CityAutocompleteRepository
import com.zagirlek.nytimes.domain.repository.CityRepository
import com.zagirlek.nytimes.domain.repository.WeatherRepository

class RepositoryModule(
    private val weatherDao: WeatherDao,
    private val cityDao: CityDao,
    private val autocompleteService: AutocompleteService,
    connectionChecker: NetworkConnectionChecker
) {
    private val authRepository: AuthRepository = MockAuthRepositoryImpl(
        networkConnectionChecker = connectionChecker
    )
    private val cityAutocompleteRepository: CityAutocompleteRepository =
        CityAutocompleteRepositoryImpl(
            autocompleteService = autocompleteService
        )
    private val cityRepository: CityRepository = CityRepositoryImpl(
        cityDao = cityDao
    )
    private val weatherRepository: WeatherRepository = WeatherRepositoryImpl(
        weatherDao = weatherDao
    )

    fun getAuthRepository(): AuthRepository = authRepository

    fun getCityAutocompleteRepository(): CityAutocompleteRepository = cityAutocompleteRepository

    fun getCityRepository(): CityRepository = cityRepository

    fun getWeatherRepository(): WeatherRepository = weatherRepository
}