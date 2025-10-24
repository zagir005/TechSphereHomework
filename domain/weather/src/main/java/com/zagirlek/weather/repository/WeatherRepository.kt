package com.zagirlek.weather.repository

import com.zagirlek.weather.model.City
import com.zagirlek.weather.model.WeatherPoint
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun add(city: City, temperature: Int): Long
    suspend fun getWeatherPointById(id: Long): WeatherPoint?
    fun getWeatherPointsFlow(): Flow<List<WeatherPoint>>
    suspend fun deleteWeatherPointById(id: Long)
}