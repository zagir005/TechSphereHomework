package com.zagirlek.nytimes.data.usecase

import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.model.WeatherPoint
import com.zagirlek.nytimes.domain.repository.WeatherRepository
import com.zagirlek.nytimes.domain.usecase.AddWeatherPointUseCase

class AddWeatherPointUseCaseImpl(
    private val weatherRepository: WeatherRepository
): AddWeatherPointUseCase {
    override suspend fun invoke(
        city: City,
        temperature: Int
    ): WeatherPoint {
        val id = weatherRepository.addWeatherPoint(city = city, temperature = temperature)
        return weatherRepository.getWeatherPointById(id)!!
    }
}