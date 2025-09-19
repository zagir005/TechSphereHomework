package com.zagirlek.nytimes.data.repository

import com.zagirlek.nytimes.core.network.service.AutocompleteService
import com.zagirlek.nytimes.data.mapper.toDomain
import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.repository.CityAutocompleteRepository

class CityAutocompleteRepositoryImpl(
    private val autocompleteService: AutocompleteService
): CityAutocompleteRepository {

    override suspend fun autocompleteSearch(query: String): Result<List<City>> =
        runCatching {
            autocompleteService.autocompleteSearch(query).map {
                it.toDomain()
            }
        }
}