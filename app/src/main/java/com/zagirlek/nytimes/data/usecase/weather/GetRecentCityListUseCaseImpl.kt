package com.zagirlek.nytimes.data.usecase.weather

import com.zagirlek.weather.model.City
import com.zagirlek.weather.repository.CityRepository
import com.zagirlek.weather.usecase.GetRecentCityListUseCase

class GetRecentCityListUseCaseImpl(
    private val cityRepository: CityRepository
): GetRecentCityListUseCase {
    override suspend fun invoke(name: String): List<City> =
        cityRepository.findCitiesByName(name)
}