package com.zagirlek.nytimes.domain.repository

import com.zagirlek.nytimes.domain.model.City

interface AutocompleteRepository {

    suspend fun autocompleteSearch(query: String): Result<List<City>>

}