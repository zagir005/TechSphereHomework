package com.zagirlek.nytimes.data.repository

import com.zagirlek.nytimes.core.local.dao.WeatherDao
import com.zagirlek.nytimes.core.local.entity.WeatherInfoEntity
import com.zagirlek.nytimes.data.mapper.toDomain
import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.model.WeatherPoint
import com.zagirlek.nytimes.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeatherRepositoryImpl(
    private val weatherDao: WeatherDao
): WeatherRepository {
    override suspend fun addWeatherPoint(
        city: City,
        degree: Int
    ): Long {
        return weatherDao.insertWeatherInfo(
            WeatherInfoEntity(
                cityId = city.id,
                degree = degree
            )
        )
    }

    override suspend fun getWeatherPointById(id: Long): WeatherPoint {
        return weatherDao.getWeatherInfoById(id).toDomain()
    }

    override fun getWeatherPoints(): Flow<List<WeatherPoint>> {
        return weatherDao.getWeatherByOfAllCities().map {
            it.toDomain()
        }
    }
}