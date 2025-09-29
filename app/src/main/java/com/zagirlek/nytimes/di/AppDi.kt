package com.zagirlek.nytimes.di

import android.content.Context
import androidx.room.Room
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.zagirlek.nytimes.core.networkchecker.NetworkConnectionChecker
import com.zagirlek.nytimes.core.networkchecker.NetworkConnectionCheckerImpl
import com.zagirlek.nytimes.data.local.NyTimesDatabase
import com.zagirlek.nytimes.data.local.dao.CityDao
import com.zagirlek.nytimes.data.local.dao.WeatherDao
import com.zagirlek.nytimes.data.network.NetworkModule
import com.zagirlek.nytimes.data.network.weather.service.AutocompleteService

class AppDi(applicationContext: Context) {
    private val database: NyTimesDatabase = Room.databaseBuilder(
        context = applicationContext,
        klass = NyTimesDatabase::class.java,
        name = "nyTimesDatabase"
    )
        .fallbackToDestructiveMigration(true)
        .build()
    private val autocompleteService = NetworkModule.autocompleteApi
    private val networkConnectionChecker: NetworkConnectionChecker =
        NetworkConnectionCheckerImpl(applicationContext)
    private val storeFactory: StoreFactory = DefaultStoreFactory()

    fun getWeatherDao(): WeatherDao = database.weatherDao()
    fun getCityDao(): CityDao = database.cityDao()
    fun getAutocompleteService(): AutocompleteService = autocompleteService
    fun getNetworkConnectionChecker(): NetworkConnectionChecker = networkConnectionChecker
    fun getStoreFactory(): StoreFactory = storeFactory
}