package com.zagirlek.nytimes.data.usecase.weather

import com.zagirlek.weather.model.City
import com.zagirlek.weather.repository.CityAutocompleteRepository
import com.zagirlek.weather.usecase.GetCityAutocompleteUseCase

class GetCityAutocompleteUseCaseImpl(
    private val autocompleteRepository: CityAutocompleteRepository
): GetCityAutocompleteUseCase {
    override suspend fun invoke(name: String): Result<List<City>> =
        autocompleteRepository.autocompleteSearch(name)
}