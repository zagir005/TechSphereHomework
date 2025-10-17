package com.zagirlek.nytimes.data.usecase.weather

import com.zagirlek.weather.repository.WeatherRepository
import com.zagirlek.weather.usecase.DeleteWeatherPointUseCase

class DeleteWeatherPointUseCaseImpl(
    private val weatherRepository: WeatherRepository
): DeleteWeatherPointUseCase {
    override suspend fun invoke(id: Long) = weatherRepository.deleteWeatherPointById(id)
}