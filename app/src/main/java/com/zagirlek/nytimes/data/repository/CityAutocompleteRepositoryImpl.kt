package com.zagirlek.nytimes.data.repository

import com.zagirlek.common.utils.runCatchingCancellable
import com.zagirlek.nytimes.data.mapper.toDomain
import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.repository.CityAutocompleteRepository
import com.zagirlek.remote.autocomplete.service.AutocompleteService

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