package com.zagirlek.weather.usecase

import com.zagirlek.weather.model.City
import com.zagirlek.weather.repository.CityRepository

class GetRecentCityListUseCase(
    private val cityRepository: CityRepository
) {
    suspend operator fun invoke(name: String): List<City> =
        cityRepository.findCitiesByName(name)
}