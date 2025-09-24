package com.zagirlek.nytimes.data.repository

import android.util.Log
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

    override suspend fun saveOrGetCity(name: String): City {
        val id = saveCity(name)
        if (id == -1L){
            val city = getCityByName(name) ?: throw IllegalStateException("Город вроде существует, но не нашли")
            Log.d("MYTAGDATA", "City ${city.id}")
            return city
        } else{
            val city = getCityById(id) ?: throw IllegalStateException("Город вроде существует, но не нашли")
            Log.d("MYTAGDATA", "City ${city.id}")
            return city
        }
    }

    override suspend fun getCityByName(name: String): City? = cityDao.findCityByName(name = name)?.toDomain()


    override suspend fun getCityById(id: Long): City? = cityDao.getCityById(id)?.toDomain()
}