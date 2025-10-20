package com.zagirlek.weather.usecase

import com.zagirlek.weather.model.City
import com.zagirlek.weather.model.WeatherPoint
import com.zagirlek.weather.repository.WeatherRepository



class AddWeatherPointUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(
        city: City,
        temperature: Int
    ): Result<WeatherPoint> = runCatching {
        val id = weatherRepository.add(city = city, temperature = temperature)
        if (id == -1L){
            throw IllegalStateException("Не удалось добавить WeatherInfoEntity")
        }

        weatherRepository.getWeatherPointById(id)
            ?: throw IllegalStateException("WeatherPoint с id=$id существует, но не найден")
    }
}