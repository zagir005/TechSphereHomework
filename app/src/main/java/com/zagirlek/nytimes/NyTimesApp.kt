package com.zagirlek.nytimes

import android.app.Application
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.nytimes.data.repository.di.RepositoryModule
import com.zagirlek.nytimes.data.usecase.di.UseCaseModuleImpl
import com.zagirlek.nytimes.di.AppDi
import com.zagirlek.nytimes.domain.UseCaseModule
import com.zagirlek.nytimes.ui.root.di.RootModule

class NyTimesApp: Application() {
    private val appDi: AppDi by lazy {
        AppDi(applicationContext = this)
    }
    private val repositoryModule: RepositoryModule by lazy {
        RepositoryModule(
            autocompleteService = appDi.coreRemoteModule.provideAutocompleteService,
            remoteNewsSource = appDi.coreRemoteModule.provideRemoteNewsSource,
            remoteNewsExtractorSource = appDi.coreRemoteModule.provideRemoteNewsExtractorSource,
            weatherDao = appDi.coreLocalModule.weatherDao(),
            cityDao = appDi.coreLocalModule.cityDao(),
            articleFullDao = appDi.coreLocalModule.articleFullDao(),
            articleLiteDao = appDi.coreLocalModule.articleLiteDao(),
            articleStatusDao = appDi.coreLocalModule.articleStatusDao(),
            connectionChecker = appDi.networkConnectionChecker
        )
    }

    private val useCaseModule: UseCaseModule by lazy {
        UseCaseModuleImpl(repositoryModule)
    }

    private val storeFactory: StoreFactory by lazy {
        appDi.storeFactory
    }

    val rootModule: RootModule by lazy {
        RootModule(
            useCaseModule = useCaseModule,
            storeFactory = storeFactory
        )
    }
}