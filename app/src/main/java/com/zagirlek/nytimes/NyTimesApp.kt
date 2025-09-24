package com.zagirlek.nytimes

import android.app.Application
import com.zagirlek.nytimes.data.repository.di.RepositoryModule
import com.zagirlek.nytimes.data.usecase.di.UseCaseModuleImpl
import com.zagirlek.nytimes.di.AppDi
import com.zagirlek.nytimes.domain.usecase.di.UseCaseModule

class NyTimesApp: Application() {
    private val appDi: AppDi by lazy {
        AppDi(this)
    }
    val repositoryModule: RepositoryModule by lazy {
        RepositoryModule(
            weatherDao = appDi.getWeatherDao(),
            cityDao = appDi.getCityDao(),
            autocompleteService = appDi.getAutocompleteService()
        )
    }

    val useCaseModule: UseCaseModule by lazy {
        UseCaseModuleImpl(repositoryModule)
    }
}