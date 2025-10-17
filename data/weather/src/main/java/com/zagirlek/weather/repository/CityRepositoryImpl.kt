package com.zagirlek.weather.repository

import com.zagirlek.local.weather.dao.CityDao
import com.zagirlek.local.weather.entity.CityEntity
import com.zagirlek.weather.mapper.toDomain
import com.zagirlek.weather.model.City

class CityRepositoryImpl(
    private val cityDao: CityDao
): CityRepository {

    override suspend fun addCity(name: String): Long = cityDao.insertCity(
        CityEntity(name = name)
        )

    override suspend fun findCitiesByName(name: String): List<City> = cityDao.getCitiesByName(name).map {
            it.toDomain()
        }

    override suspend fun getCityByName(name: String): City? = cityDao.findCityByName(name = name)?.toDomain()


    override suspend fun getCityById(id: Long): City? = cityDao.getCityById(id)?.toDomain()
}