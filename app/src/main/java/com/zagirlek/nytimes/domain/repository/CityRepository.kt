package com.zagirlek.nytimes.domain.repository

import com.zagirlek.nytimes.domain.model.City

interface CityRepository {
    suspend fun saveCity(name: String): Long
    suspend fun findCitiesByName(name: String): List<City>
    suspend fun saveOrGetCity(name: String): City
    suspend fun getCityByName(name: String): City?
    suspend fun getCityById(id: Long): City?
}