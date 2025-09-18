package com.zagirlek.nytimes.data.repository

import com.zagirlek.nytimes.core.local.dao.CityDao
import com.zagirlek.nytimes.core.local.entity.CityEntity
import com.zagirlek.nytimes.data.mapper.toDomain
import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.repository.CityRepository

class CityRepositoryImpl(
    private val cityDao: CityDao
): CityRepository {
    override suspend fun saveCity(name: String, id: Long): Long {
        return cityDao.insertCity(
            CityEntity(
                name = name,
                id = id
            )
        )
    }

    override suspend fun findCity(query: String): List<City> {
        return cityDao.getCitiesByName(query).map {
            it.toDomain()
        }
    }

    override suspend fun getCityById(id: Long): City? {
        return cityDao.getCityById(id)?.toDomain()
    }


}