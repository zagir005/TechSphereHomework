package com.zagirlek.nytimes.data.usecase.weather

import com.zagirlek.common.utils.runCatchingCancellable
import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.repository.CityRepository
import com.zagirlek.nytimes.domain.usecase.weather.GetOrPutCityUseCase

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