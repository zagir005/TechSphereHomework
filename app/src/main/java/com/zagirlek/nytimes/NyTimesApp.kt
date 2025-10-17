package com.zagirlek.nytimes

import android.app.Application
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.auth.di.AuthDomainModule
import com.zagirlek.nytimes.data.repository.di.RepositoryModule
import com.zagirlek.nytimes.data.usecase.di.UseCaseModuleImpl
import com.zagirlek.nytimes.di.AppDi
import com.zagirlek.nytimes.domain.UseCaseModule
import com.zagirlek.nytimes.ui.root.di.RootModule
import com.zagirlek.weather.di.WeatherDomainModule

class NyTimesApp: Application() {
    private val appDi: AppDi by lazy {
        AppDi(applicationContext = this)
    }
    private val repositoryModule: RepositoryModule by lazy {
        RepositoryModule(
            remoteNewsSource = appDi.coreRemoteModule.provideRemoteNewsSource,
            remoteNewsExtractorSource = appDi.coreRemoteModule.provideRemoteNewsExtractorSource,
            articleFullDao = appDi.coreLocalModule.articleFullDao(),
            articleLiteDao = appDi.coreLocalModule.articleLiteDao(),
            articleStatusDao = appDi.coreLocalModule.articleStatusDao(),
            connectionChecker = appDi.networkConnectionChecker
        )
    }

    private val useCaseModule: UseCaseModule by lazy {
        UseCaseModuleImpl(repositoryModule)
    }
    private val authDomainModule: AuthDomainModule by lazy {
        AuthDomainModule(
            authRepository = appDi.authDataModule.authRepository
        )
    }
    private val weatherDomainModule: WeatherDomainModule by lazy {
        WeatherDomainModule(
            cityAutocompleteRepository = appDi.weatherDataModule.cityAutocompleteRepository,
            cityRepository = appDi.weatherDataModule.cityRepository,
            weatherRepository = appDi.weatherDataModule.weatherRepository
        )
    }

    private val storeFactory: StoreFactory by lazy {
        appDi.storeFactory
    }

    val rootModule: RootModule by lazy {
        RootModule(
            useCaseModule = useCaseModule,
            storeFactory = storeFactory,
            authDomainModule = authDomainModule,
            weatherDomainModule = weatherDomainModule
        )
    }
}