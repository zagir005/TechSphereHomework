package com.zagirlek.weather.repository

import com.zagirlek.weather.model.City

interface CityRepository {
    suspend fun add(name: String): Long
    suspend fun findCitiesByName(name: String): List<City>
    suspend fun getCityByName(name: String): City?
    suspend fun getCityById(id: Long): City?
}