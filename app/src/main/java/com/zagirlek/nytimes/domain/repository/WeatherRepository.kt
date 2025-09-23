package com.zagirlek.nytimes.domain.repository

import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.model.WeatherPoint
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun addWeatherPoint(city: City, degree: Int): Long
    suspend fun getWeatherPointById(id: Long): WeatherPoint
    fun getWeatherPoints(): Flow<List<WeatherPoint>>
    suspend fun deleteWeatherPointById(id: Long)
}