package com.zagirlek.weather.usecase

import com.zagirlek.weather.model.City
import com.zagirlek.weather.repository.CityAutocompleteRepository

class GetCityAutocompleteUseCase(
    private val autocompleteRepository: CityAutocompleteRepository
) {
    suspend operator fun invoke(name: String): Result<List<City>> =
        autocompleteRepository.autocompleteSearch(name)
}