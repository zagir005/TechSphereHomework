package com.zagirlek.nytimes.data.repository.di

import com.zagirlek.nytimes.core.networkchecker.NetworkConnectionChecker
import com.zagirlek.nytimes.data.local.dao.CityDao
import com.zagirlek.nytimes.data.local.dao.WeatherDao
import com.zagirlek.nytimes.data.network.weather.service.AutocompleteService
import com.zagirlek.nytimes.data.newsmanager.NewsManager
import com.zagirlek.nytimes.data.repository.CityAutocompleteRepositoryImpl
import com.zagirlek.nytimes.data.repository.CityRepositoryImpl
import com.zagirlek.nytimes.data.repository.MockAuthRepositoryImpl
import com.zagirlek.nytimes.data.repository.NewsRepositoryImpl
import com.zagirlek.nytimes.data.repository.WeatherRepositoryImpl
import com.zagirlek.nytimes.domain.repository.AuthRepository
import com.zagirlek.nytimes.domain.repository.CityAutocompleteRepository
import com.zagirlek.nytimes.domain.repository.CityRepository
import com.zagirlek.nytimes.domain.repository.NewsRepository
import com.zagirlek.nytimes.domain.repository.WeatherRepository

class RepositoryModule(
    private val weatherDao: WeatherDao,
    private val cityDao: CityDao,
    private val autocompleteService: AutocompleteService,
    private val newsManager: NewsManager,
    connectionChecker: NetworkConnectionChecker
) {
    private val authRepository: AuthRepository by lazy {
        MockAuthRepositoryImpl(
            networkConnectionChecker = connectionChecker
        )
    }
    private val cityAutocompleteRepository: CityAutocompleteRepository by lazy {
        CityAutocompleteRepositoryImpl(
            autocompleteService = autocompleteService
        )
    }
    private val cityRepository: CityRepository by lazy {
        CityRepositoryImpl(
            cityDao = cityDao
        )
    }
    private val weatherRepository: WeatherRepository by lazy {
        WeatherRepositoryImpl(
            weatherDao = weatherDao
        )
    }
    private val newsRepository: NewsRepository by lazy {
        NewsRepositoryImpl(
            newsManager = newsManager
        )
    }

    fun getNewsRepository(): NewsRepository = newsRepository
    fun getAuthRepository(): AuthRepository = authRepository
    fun getCityAutocompleteRepository(): CityAutocompleteRepository = cityAutocompleteRepository
    fun getCityRepository(): CityRepository = cityRepository
    fun getWeatherRepository(): WeatherRepository = weatherRepository
}