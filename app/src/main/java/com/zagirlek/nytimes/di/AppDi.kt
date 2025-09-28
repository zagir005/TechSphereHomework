package com.zagirlek.nytimes.di

import android.content.Context
import androidx.room.Room
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.zagirlek.nytimes.BuildConfig
import com.zagirlek.nytimes.core.interceptor.APIKeyInterceptor
import com.zagirlek.nytimes.core.networkchecker.NetworkConnectionChecker
import com.zagirlek.nytimes.core.networkchecker.NetworkConnectionCheckerImpl
import com.zagirlek.nytimes.data.local.NyTimesDatabase
import com.zagirlek.nytimes.data.local.dao.CityDao
import com.zagirlek.nytimes.data.local.dao.WeatherDao
import com.zagirlek.nytimes.data.network.service.AutocompleteService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppDi(applicationContext: Context) {
    private val database: NyTimesDatabase = Room.databaseBuilder(
        context = applicationContext,
        klass = NyTimesDatabase::class.java,
        name = "nyTimesDatabase"
    )
        .fallbackToDestructiveMigration(true)
        .build()

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(APIKeyInterceptor())
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
    private val retrofitClient: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: AutocompleteService by lazy {
        retrofitClient.create(AutocompleteService::class.java)
    }
    private val networkConnectionChecker: NetworkConnectionChecker =
        NetworkConnectionCheckerImpl(applicationContext)

    private val storeFactory: StoreFactory = DefaultStoreFactory()

    fun getWeatherDao(): WeatherDao = database.weatherDao()
    fun getCityDao(): CityDao = database.cityDao()
    fun getAutocompleteService(): AutocompleteService = service
    fun getNetworkConnectionChecker(): NetworkConnectionChecker = networkConnectionChecker
    fun getStoreFactory(): StoreFactory = storeFactory
}