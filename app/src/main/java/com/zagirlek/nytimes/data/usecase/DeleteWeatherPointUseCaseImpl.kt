package com.zagirlek.nytimes.data.usecase

import com.zagirlek.nytimes.domain.repository.WeatherRepository
import com.zagirlek.nytimes.domain.usecase.DeleteWeatherPointUseCase

class DeleteWeatherPointUseCaseImpl(
    private val weatherRepository: WeatherRepository
): DeleteWeatherPointUseCase {
    override suspend fun invoke(id: Long) = weatherRepository.deleteWeatherPointById(id)
}