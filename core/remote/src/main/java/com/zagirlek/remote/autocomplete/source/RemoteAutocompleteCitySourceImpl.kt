package com.zagirlek.remote.autocomplete.source

import com.zagirlek.remote.autocomplete.dto.CityDTO
import com.zagirlek.remote.autocomplete.service.AutocompleteService

class RemoteAutocompleteCitySourceImpl(
    private val autocompleteService: AutocompleteService
): RemoteAutocompleteCitySource {
    override suspend fun autocompleteSearch(filter: String): List<CityDTO> =
        autocompleteService.autocompleteSearch(filter)
}