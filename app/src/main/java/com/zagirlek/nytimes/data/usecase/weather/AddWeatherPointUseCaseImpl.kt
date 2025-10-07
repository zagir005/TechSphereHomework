package com.zagirlek.nytimes.data.usecase.weather

import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.model.WeatherPoint
import com.zagirlek.nytimes.domain.repository.WeatherRepository
import com.zagirlek.nytimes.domain.usecase.weather.AddWeatherPointUseCase

class AddWeatherPointUseCaseImpl(
    private val weatherRepository: WeatherRepository
): AddWeatherPointUseCase {
    override suspend fun invoke(
        city: City,
        temperature: Int
    ): Result<WeatherPoint> = runCatching {
        val id = weatherRepository.addWeatherPoint(city = city, temperature = temperature)
        if (id == -1L){
            throw IllegalStateException("Не удалось добавить WeatherInfoEntity")
        }

        weatherRepository.getWeatherPointById(id)
            ?: throw IllegalStateException("WeatherPoint с id=$id существует, но не найден")
    }
}