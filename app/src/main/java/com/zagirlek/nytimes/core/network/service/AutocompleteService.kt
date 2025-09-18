package com.zagirlek.nytimes.core.network.service

import com.zagirlek.nytimes.core.network.dto.CityDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface AutocompleteService {

    //я знаю что прокидывать так ключ плохо, опаздываю по дедлайну, простите
    @GET("search.json")
    suspend fun autocompleteSearch(
        @Query("q") query: String,
        @Query("key") key: String = "dd9eadf389194d2c828170710251809"
    ): List<CityDTO>
}