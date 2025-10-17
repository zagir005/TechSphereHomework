package com.zagirlek.nytimes.data.repository

import com.zagirlek.common.utils.runCatchingCancellable
import com.zagirlek.nytimes.data.mapper.toDomain
import com.zagirlek.remote.autocomplete.service.AutocompleteService
import com.zagirlek.weather.model.City
import com.zagirlek.weather.repository.CityAutocompleteRepository

class CityAutocompleteRepositoryImpl(
    private val autocompleteService: AutocompleteService
): CityAutocompleteRepository {
    override suspend fun autocompleteSearch(query: String): Result<List<City>> =
        runCatchingCancellable {
            autocompleteService.autocompleteSearch(query).map {
                it.toDomain()
            }
        }
}