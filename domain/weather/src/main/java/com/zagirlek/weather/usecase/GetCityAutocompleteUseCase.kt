package com.zagirlek.weather.usecase

import com.zagirlek.weather.model.City

fun interface GetCityAutocompleteUseCase {
    suspend operator fun invoke(name: String): Result<List<City>>
}