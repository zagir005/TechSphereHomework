package com.zagirlek.nytimes.data.usecase

import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.repository.CityRepository
import com.zagirlek.nytimes.domain.usecase.GetOrPutCityUseCase

class GetOrPutCityUseCaseImpl(
    private val cityRepository: CityRepository
): GetOrPutCityUseCase {
    override suspend fun invoke(name: String): City =
        cityRepository.getOrPutCity(name)
}