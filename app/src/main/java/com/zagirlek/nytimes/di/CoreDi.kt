package com.zagirlek.nytimes.di

import android.content.Context
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.zagirlek.android.CoreAndroidModule
import com.zagirlek.android.networkchecker.NetworkConnectionChecker
import com.zagirlek.authmanager.di.AuthManagerModule
import com.zagirlek.local.di.CoreLocalModule
import com.zagirlek.nytimes.BuildConfig
import com.zagirlek.remote.di.CoreRemoteModule
import com.zagirlek.remote.di.CoreRemoteModuleImpl
import com.zagirlek.remote.di.MockCoreRemoteModule

class CoreDi(
    private val applicationContext: Context
) {
    val coreLocalModule: CoreLocalModule by lazy {
        CoreLocalModule(applicationContext)
    }
    val coreRemoteModule: CoreRemoteModule by lazy {
        if (BuildConfig.DEBUG)
            MockCoreRemoteModule()
        else
            CoreRemoteModuleImpl(
                autocompleteBaseUrl = BuildConfig.WEATHER_BASE_URL,
                extractorBaseUrl = BuildConfig.EXTRACTOR_BASE_URL,
                newsBaseUrl = BuildConfig.NEWS_BASE_URL,
                newsDomains = BuildConfig.AVAILABLE_DOMAINS,
                newsKey = listOf(
                    BuildConfig.NEWS_API_KEY,
                    BuildConfig.NEWS_API_KEY_1,
                    BuildConfig.NEWS_API_KEY_2
                ),
                extractorKey = listOf(
                    BuildConfig.EXTRACTOR_API_KEY,
                    BuildConfig.EXTRACTOR_API_KEY_1,
                    BuildConfig.EXTRACTOR_API_KEY_2,
                    BuildConfig.EXTRACTOR_API_KEY_3,
                ),
                autocompleteKey = listOf(
                    BuildConfig.WEATHER_API_KEY
                )
            )
    }
    val networkConnectionChecker: NetworkConnectionChecker by lazy {
        CoreAndroidModule.provideNetworkConnectionChecker(applicationContext)
    }
    val storeFactory: StoreFactory by lazy {
        DefaultStoreFactory()
    }
    val authManager by lazy {
        AuthManagerModule(
            userDao = coreLocalModule.userDao()
        ).getAuthManager(
            context = applicationContext
        )
    }
}