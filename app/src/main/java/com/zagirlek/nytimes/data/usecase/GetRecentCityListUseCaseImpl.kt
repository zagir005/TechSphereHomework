package com.zagirlek.nytimes.data.usecase

import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.repository.CityRepository
import com.zagirlek.nytimes.domain.usecase.GetRecentCityListUseCase

class GetRecentCityListUseCaseImpl(
    private val cityRepository: CityRepository
): GetRecentCityListUseCase {
    override suspend fun invoke(name: String): List<City> =
        cityRepository.findCitiesByName(name)
}