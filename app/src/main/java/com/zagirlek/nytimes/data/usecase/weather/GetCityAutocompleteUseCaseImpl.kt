package com.zagirlek.nytimes.data.usecase.weather

import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.repository.CityAutocompleteRepository
import com.zagirlek.nytimes.domain.usecase.weather.GetCityAutocompleteUseCase

class GetCityAutocompleteUseCaseImpl(
    private val autocompleteRepository: CityAutocompleteRepository
): GetCityAutocompleteUseCase {
    override suspend fun invoke(name: String): Result<List<City>> =
        autocompleteRepository.autocompleteSearch(name)
}