package com.zagirlek.weather.usecase


import com.zagirlek.weather.model.WeatherPoint
import com.zagirlek.weather.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class GetWeatherPointsHistoryFlowUseCase(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(): Flow<List<WeatherPoint>> =
        weatherRepository.getWeatherPointsFlow()
}