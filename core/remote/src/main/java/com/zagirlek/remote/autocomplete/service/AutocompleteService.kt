package com.zagirlek.remote.autocomplete.service

import com.zagirlek.remote.autocomplete.dto.CityDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface AutocompleteService {
    @GET("search.json")
    suspend fun autocompleteSearch(
        @Query("q") query: String
    ): List<CityDTO>
}