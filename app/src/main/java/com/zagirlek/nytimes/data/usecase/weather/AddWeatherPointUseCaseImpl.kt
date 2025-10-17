package com.zagirlek.nytimes.data.usecase.weather

import com.zagirlek.weather.model.City
import com.zagirlek.weather.model.WeatherPoint
import com.zagirlek.weather.repository.WeatherRepository
import com.zagirlek.weather.usecase.AddWeatherPointUseCase


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