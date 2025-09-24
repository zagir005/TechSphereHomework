package com.zagirlek.nytimes.di

import android.content.Context
import androidx.room.Room
import com.zagirlek.nytimes.BuildConfig
import com.zagirlek.nytimes.core.interceptor.APIKeyInterceptor
import com.zagirlek.nytimes.data.local.NyTimesDatabase
import com.zagirlek.nytimes.data.local.dao.CityDao
import com.zagirlek.nytimes.data.local.dao.WeatherDao
import com.zagirlek.nytimes.data.network.service.AutocompleteService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppDi(private val context: Context) {
    private val db = Room.databaseBuilder(
        context = context,
        klass = NyTimesDatabase::class.java,
        name = "nyTimesDatabase"
    )
        .fallbackToDestructiveMigration(true)
        .build()


    private val client = OkHttpClient.Builder()
        .addInterceptor(APIKeyInterceptor())
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
    private val autocompleteService = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AutocompleteService::class.java)

    fun getWeatherDao(): WeatherDao = db.weatherDao()

    fun getCityDao(): CityDao = db.cityDao()

    fun getAutocompleteService(): AutocompleteService = autocompleteService
}