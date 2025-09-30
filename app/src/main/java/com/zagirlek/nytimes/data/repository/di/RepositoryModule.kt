package com.zagirlek.nytimes.data.repository.di

import com.zagirlek.nytimes.core.networkchecker.NetworkConnectionChecker
import com.zagirlek.nytimes.data.local.NyTimesDatabase
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
    private val database: NyTimesDatabase,
    private val autocompleteService: AutocompleteService,
    private val newsManager: NewsManager,
    connectionChecker: NetworkConnectionChecker
) {
    val authRepository: AuthRepository by lazy {
        MockAuthRepositoryImpl(
            networkConnectionChecker = connectionChecker
        )
    }
    val cityAutocompleteRepository: CityAutocompleteRepository by lazy {
        CityAutocompleteRepositoryImpl(
            autocompleteService = autocompleteService
        )
    }
    val cityRepository: CityRepository by lazy {
        CityRepositoryImpl(
            cityDao = database.cityDao()
        )
    }
    val weatherRepository: WeatherRepository by lazy {
        WeatherRepositoryImpl(
            weatherDao = database.weatherDao()
        )
    }
    val newsRepository: NewsRepository by lazy {
        NewsRepositoryImpl(
            newsManager = newsManager,
            database = database
        )
    }
}