package com.zagirlek.nytimes.data.usecase.weather

import com.zagirlek.nytimes.domain.repository.WeatherRepository
import com.zagirlek.nytimes.domain.usecase.weather.DeleteWeatherPointUseCase

class DeleteWeatherPointUseCaseImpl(
    private val weatherRepository: WeatherRepository
): DeleteWeatherPointUseCase {
    override suspend fun invoke(id: Long) = weatherRepository.deleteWeatherPointById(id)
}