package com.zagirlek.nytimes

import android.app.Application
import com.zagirlek.nytimes.data.repository.CityAutocompleteRepositoryImpl
import com.zagirlek.nytimes.data.repository.CityRepositoryImpl
import com.zagirlek.nytimes.data.repository.MockAuthRepositoryImpl
import com.zagirlek.nytimes.data.repository.WeatherRepositoryImpl
import com.zagirlek.nytimes.di.AppDi
import com.zagirlek.nytimes.domain.repository.AuthRepository
import com.zagirlek.nytimes.domain.repository.CityAutocompleteRepository
import com.zagirlek.nytimes.domain.repository.CityRepository
import com.zagirlek.nytimes.domain.repository.WeatherRepository

class NyTimesApp: Application() {
    private val appDi: AppDi by lazy {
        AppDi(this)
    }

    val weatherRepository: WeatherRepository by lazy {
        WeatherRepositoryImpl(
            appDi.getWeatherDao()
        )
    }

    val cityRepository: CityRepository by lazy {
        CityRepositoryImpl(
            appDi.getCityDao()
        )
    }

    val cityAutocompleteRepository: CityAutocompleteRepository by lazy {
        CityAutocompleteRepositoryImpl(
            autocompleteService = appDi.getAutocompleteService()
        )
    }

    val authRepository: AuthRepository by lazy {
        MockAuthRepositoryImpl()
    }
}