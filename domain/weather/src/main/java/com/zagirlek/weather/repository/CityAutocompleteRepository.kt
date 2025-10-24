package com.zagirlek.weather.repository

import com.zagirlek.weather.model.City

interface CityAutocompleteRepository {
    suspend fun autocompleteSearch(filter: String): Result<List<City>>
}