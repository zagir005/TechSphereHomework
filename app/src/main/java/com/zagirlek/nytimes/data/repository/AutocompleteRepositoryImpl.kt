package com.zagirlek.nytimes.data.repository

import com.zagirlek.nytimes.core.network.service.AutocompleteService
import com.zagirlek.nytimes.data.mapper.toDomain
import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.repository.AutocompleteRepository

class AutocompleteRepositoryImpl(
    private val autocompleteService: AutocompleteService
): AutocompleteRepository {

    override suspend fun autocompleteSearch(query: String): Result<List<City>> =
        runCatching {
            autocompleteService.autocompleteSearch(query).map {
                it.toDomain()
            }
        }
}