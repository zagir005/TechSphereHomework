package com.zagirlek.remote.autocomplete.source

import com.zagirlek.remote.autocomplete.dto.CityDTO

interface RemoteAutocompleteCitySource {
    suspend fun autocompleteSearch(
        filter: String
    ): List<CityDTO>
}