package com.zagirlek.nytimes.data.repository

import com.zagirlek.local.weather.dao.WeatherDao
import com.zagirlek.local.weather.entity.WeatherInfoEntity
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
        temperature: Int
    ): Long {
        return weatherDao.insertWeatherInfo(
            WeatherInfoEntity(
                cityId = city.id,
                temperature = temperature
            )
        )
    }

    override suspend fun getWeatherPointById(id: Long): WeatherPoint? = weatherDao.getWeatherInfoById(id)?.toDomain()

    override fun getWeatherPointsFlow(): Flow<List<WeatherPoint>> = weatherDao.getWeatherByOfAllCities().map {
            it.toDomain()
        }

    override suspend fun deleteWeatherPointById(id: Long) = weatherDao.deleteWeatherInfoById(id)
}