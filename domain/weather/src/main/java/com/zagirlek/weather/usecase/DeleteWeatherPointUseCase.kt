package com.zagirlek.weather.usecase

import com.zagirlek.weather.repository.WeatherRepository

class DeleteWeatherPointUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(id: Long) = weatherRepository.deleteWeatherPointById(id)
}