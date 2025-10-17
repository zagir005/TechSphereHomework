package com.zagirlek.weather.repository


import com.zagirlek.common.utils.runCatchingCancellable
import com.zagirlek.remote.autocomplete.service.AutocompleteService
import com.zagirlek.weather.mapper.toDomain
import com.zagirlek.weather.model.City

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