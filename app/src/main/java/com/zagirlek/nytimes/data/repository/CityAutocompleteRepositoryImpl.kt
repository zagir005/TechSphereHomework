package com.zagirlek.nytimes.data.repository

import android.util.Log
import com.zagirlek.nytimes.data.mapper.toDomain
import com.zagirlek.nytimes.data.network.service.AutocompleteService
import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.repository.CityAutocompleteRepository

class CityAutocompleteRepositoryImpl(
    private val autocompleteService: AutocompleteService
): CityAutocompleteRepository {
    override suspend fun autocompleteSearch(query: String): Result<List<City>> =
        runCatching {
            val cities = autocompleteService.autocompleteSearch(query).map {
                it.toDomain()
            }
            Log.d("MYTAGAUTOCOMPLETE", cities.joinToString())
            cities
        }
}