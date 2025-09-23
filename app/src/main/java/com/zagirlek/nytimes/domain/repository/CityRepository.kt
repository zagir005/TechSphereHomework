package com.zagirlek.nytimes.domain.repository

import com.zagirlek.nytimes.domain.model.City

interface CityRepository {
    suspend fun saveCity(name: String, id: Long = 0): Long
    suspend fun findCity(query: String): List<City>
    suspend fun getCityById(id: Long): City?
}