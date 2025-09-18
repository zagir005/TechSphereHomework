package com.zagirlek.nytimes.domain.repository

import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.model.WeatherPoint
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun addWeatherPoint(city: City, degree: Int)

    fun getWeatherPoints(): Flow<List<WeatherPoint>>
}