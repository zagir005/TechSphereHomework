package com.zagirlek.weather.usecase

import com.zagirlek.weather.model.City
import com.zagirlek.weather.model.WeatherPoint

fun interface AddWeatherPointUseCase {
    suspend operator fun invoke(city: City, temperature: Int): Result<WeatherPoint>
}