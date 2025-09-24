package com.zagirlek.nytimes.data.repository

import com.zagirlek.nytimes.data.local.dao.CityDao
import com.zagirlek.nytimes.data.local.entity.CityEntity
import com.zagirlek.nytimes.data.mapper.toDomain
import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.repository.CityRepository

class CityRepositoryImpl(
    private val cityDao: CityDao
): CityRepository {

    override suspend fun saveCity(name: String): Long = cityDao.insertCity(
            CityEntity(name = name)
        )

    override suspend fun findCitiesByName(name: String): List<City> = cityDao.getCitiesByName(name).map {
            it.toDomain()
        }

    override suspend fun getOrPutCity(name: String): City {
        val id = saveCity(name)
        return if (id == -1L) getCityByName(name)!! else getCityById(id)!!
    }

    override suspend fun getCityByName(name: String): City? = cityDao.findCityByName(name = name)?.toDomain()


    override suspend fun getCityById(id: Long): City? = cityDao.getCityById(id)?.toDomain()
}