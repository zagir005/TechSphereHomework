package com.zagirlek.nytimes

import android.app.Application
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.nytimes.data.repository.di.RepositoryModule
import com.zagirlek.nytimes.data.usecase.di.UseCaseModuleImpl
import com.zagirlek.nytimes.di.AppDi
import com.zagirlek.nytimes.domain.usecase.di.UseCaseModule
import com.zagirlek.nytimes.ui.root.di.RootModule

class NyTimesApp: Application() {
    private val appDi: AppDi by lazy {
        AppDi(this)
    }
    private val repositoryModule: RepositoryModule by lazy {
        RepositoryModule(
            database = appDi.getDatabase(),
            networkModule = appDi.networkModule,
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