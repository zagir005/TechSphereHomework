package com.zagirlek.weather.repository

import com.zagirlek.weather.model.City

interface CityAutocompleteRepository {
    suspend fun autocompleteSearch(query: String): Result<List<City>>
}