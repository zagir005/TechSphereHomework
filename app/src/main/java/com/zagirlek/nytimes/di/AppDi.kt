package com.zagirlek.nytimes.di

import android.content.Context
import androidx.room.Room
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.zagirlek.nytimes.core.networkchecker.NetworkConnectionChecker
import com.zagirlek.nytimes.core.networkchecker.NetworkConnectionCheckerImpl
import com.zagirlek.nytimes.data.local.NyTimesDatabase
import com.zagirlek.nytimes.data.network.NetworkModule
import com.zagirlek.nytimes.data.network.weather.service.AutocompleteService
import com.zagirlek.nytimes.data.newsmanager.NewsManager
import com.zagirlek.nytimes.data.newsmanager.NewsManagerImpl

class AppDi(applicationContext: Context) {
    private val database: NyTimesDatabase = Room.databaseBuilder(
        context = applicationContext,
        klass = NyTimesDatabase::class.java,
        name = "nyTimesDatabase"
    )
        .fallbackToDestructiveMigration(true)
        .build()
    private val autocompleteService = NetworkModule.autocompleteApi
    private val newsService = NetworkModule.newsApi
    private val newsExtractorService = NetworkModule.newsExtractorApi
    val networkConnectionChecker: NetworkConnectionChecker by lazy {
        NetworkConnectionCheckerImpl(applicationContext)
    }
    val storeFactory: StoreFactory by lazy {
        DefaultStoreFactory()
    }
    val newsManager: NewsManager by lazy {
        NewsManagerImpl(
            newsService = newsService,
            newsExtractorService = newsExtractorService
        )
    }

    fun getDatabase(): NyTimesDatabase = database
    fun getAutocompleteService(): AutocompleteService = autocompleteService

}