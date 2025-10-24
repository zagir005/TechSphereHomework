package com.zagirlek.weather.repository


import com.zagirlek.common.utils.runCatchingCancellable
import com.zagirlek.remote.autocomplete.source.RemoteAutocompleteCitySource
import com.zagirlek.weather.mapper.toDomain
import com.zagirlek.weather.model.City

class CityAutocompleteRepositoryImpl(
    private val autocompleteCitySource: RemoteAutocompleteCitySource
): CityAutocompleteRepository {
    override suspend fun autocompleteSearch(filter: String): Result<List<City>> =
        runCatchingCancellable {
            autocompleteCitySource.autocompleteSearch(filter).map {
                it.toDomain()
            }
        }
}