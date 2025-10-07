package com.zagirlek.nytimes.data.network.weather.service

import com.zagirlek.nytimes.data.network.weather.dto.CityDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface AutocompleteService {
    @GET("search.json")
    suspend fun autocompleteSearch(
        @Query("q") query: String
    ): List<CityDTO>
}