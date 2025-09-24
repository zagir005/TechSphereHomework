package com.zagirlek.nytimes.domain.usecase

import com.zagirlek.nytimes.domain.model.City

fun interface GetCityAutocompleteUseCase {
    suspend operator fun invoke(name: String): Result<List<City>>
}