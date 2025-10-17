package com.zagirlek.nytimes.data.usecase.weather

import com.zagirlek.common.utils.runCatchingCancellable
import com.zagirlek.weather.model.City
import com.zagirlek.weather.repository.CityRepository
import com.zagirlek.weather.usecase.GetOrPutCityUseCase

class GetOrPutCityUseCaseImpl(
    private val cityRepository: CityRepository
): GetOrPutCityUseCase {
    override suspend fun invoke(name: String): Result<City> = runCatchingCancellable {
        val id = cityRepository.addCity(name)
        if (id == -1L)
            cityRepository.getCityByName(name) ?: throw IllegalStateException("Город есть, но не найден")
        else
            cityRepository.getCityById(id) ?: throw IllegalStateException("Город добавлен, но не найден")
    }
}