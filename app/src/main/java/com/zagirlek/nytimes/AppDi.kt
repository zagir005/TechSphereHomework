package com.zagirlek.nytimes

import android.content.Context
import androidx.room.Room
import com.zagirlek.nytimes.core.local.NyTimesDatabase
import com.zagirlek.nytimes.core.local.dao.CityDao
import com.zagirlek.nytimes.core.local.dao.WeatherDao
import com.zagirlek.nytimes.core.network.service.AutocompleteService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class AppDi(private val context: Context) {

    private val db = Room.databaseBuilder(
        context = context,
        klass = NyTimesDatabase::class.java,
        name = "nytimesDatabase"
    ).build()

    private val autocompleteService = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AutocompleteService::class.java)

    fun getWeatherDao(): WeatherDao = db.weatherDao()

    fun getCityDao(): CityDao = db.cityDao()

    fun getAutocompleteService(): AutocompleteService = autocompleteService
}