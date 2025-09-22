package com.zagirlek.nytimes.di

import android.content.Context
import androidx.room.Room
import com.zagirlek.nytimes.data.local.NyTimesDatabase
import com.zagirlek.nytimes.data.local.dao.CityDao
import com.zagirlek.nytimes.data.local.dao.WeatherDao
import com.zagirlek.nytimes.data.network.service.AutocompleteService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppDi(private val context: Context) {

    private val db = Room.databaseBuilder(
        context = context,
        klass = NyTimesDatabase::class.java,
        name = "nytimesDatabase"
    )
        .fallbackToDestructiveMigration(true)
        .build()

    private val autocompleteService = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AutocompleteService::class.java)

    fun getWeatherDao(): WeatherDao = db.weatherDao()

    fun getCityDao(): CityDao = db.cityDao()

    fun getAutocompleteService(): AutocompleteService = autocompleteService
}