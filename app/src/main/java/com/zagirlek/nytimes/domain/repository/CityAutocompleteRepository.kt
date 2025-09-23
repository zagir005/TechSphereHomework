package com.zagirlek.nytimes.domain.repository

import com.zagirlek.nytimes.domain.model.City

interface CityAutocompleteRepository {
    suspend fun autocompleteSearch(query: String): Result<List<City>>
}