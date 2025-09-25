package com.zagirlek.nytimes.domain.usecase

import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.model.WeatherPoint

fun interface AddWeatherPointUseCase {
    suspend operator fun invoke(city: City, temperature: Int): Result<WeatherPoint>
}