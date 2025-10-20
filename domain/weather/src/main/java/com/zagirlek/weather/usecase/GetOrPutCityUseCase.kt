package com.zagirlek.weather.usecase

import com.zagirlek.common.utils.runCatchingCancellable
import com.zagirlek.weather.model.City
import com.zagirlek.weather.repository.CityRepository

class GetOrPutCityUseCase(
    private val cityRepository: CityRepository
) {
    suspend operator fun invoke(name: String): Result<City> = runCatchingCancellable {
        val id = cityRepository.add(name)
        if (id == -1L)
            cityRepository.getCityByName(name) ?: throw IllegalStateException("Город есть, но не найден")
        else
            cityRepository.getCityById(id) ?: throw IllegalStateException("Город добавлен, но не найден")
    }
}