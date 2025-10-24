package com.zagirlek.weather.di

import com.zagirlek.local.weather.dao.CityDao
import com.zagirlek.local.weather.dao.WeatherDao
import com.zagirlek.remote.autocomplete.source.RemoteAutocompleteCitySource
import com.zagirlek.weather.repository.CityAutocompleteRepository
import com.zagirlek.weather.repository.CityAutocompleteRepositoryImpl
import com.zagirlek.weather.repository.CityRepository
import com.zagirlek.weather.repository.CityRepositoryImpl
import com.zagirlek.weather.repository.WeatherRepository
import com.zagirlek.weather.repository.WeatherRepositoryImpl

class WeatherDataModule(
    private val autocompleteCitySource: RemoteAutocompleteCitySource,
    private val cityDao: CityDao,
    private val weatherDao: WeatherDao
) {
    val cityAutocompleteRepository: CityAutocompleteRepository by lazy {
        CityAutocompleteRepositoryImpl(autocompleteCitySource = autocompleteCitySource)
    }

    val cityRepository: CityRepository by lazy {
        CityRepositoryImpl(cityDao = cityDao)
    }

    val weatherRepository: WeatherRepository by lazy {
        WeatherRepositoryImpl(weatherDao)
    }
}