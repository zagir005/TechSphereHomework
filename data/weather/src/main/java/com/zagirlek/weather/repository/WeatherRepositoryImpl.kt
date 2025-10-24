package com.zagirlek.weather.repository

import com.zagirlek.local.weather.dao.WeatherDao
import com.zagirlek.local.weather.entity.WeatherInfoEntity
import com.zagirlek.weather.mapper.toDomain
import com.zagirlek.weather.model.City
import com.zagirlek.weather.model.WeatherPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeatherRepositoryImpl(
    private val weatherDao: WeatherDao
): WeatherRepository {
    override suspend fun add(
        city: City,
        temperature: Int
    ): Long = weatherDao.insertWeatherInfo(
            WeatherInfoEntity(
                cityId = city.id,
                temperature = temperature
            )
        )
    override suspend fun getWeatherPointById(id: Long): WeatherPoint? = weatherDao.getWeatherInfoById(id)?.toDomain()
    override fun getWeatherPointsFlow(): Flow<List<WeatherPoint>> = weatherDao.getWeatherByOfAllCities().map {
            it.toDomain()
        }
    override suspend fun deleteWeatherPointById(id: Long) = weatherDao.deleteWeatherInfoById(id)
}