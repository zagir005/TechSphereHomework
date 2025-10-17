package com.zagirlek.nytimes.data.usecase.weather


import com.zagirlek.weather.model.WeatherPoint
import com.zagirlek.weather.repository.WeatherRepository
import com.zagirlek.weather.usecase.GetWeatherPointsHistoryFlowUseCase
import kotlinx.coroutines.flow.Flow

class GetWeatherPointsHistoryFlowUseCaseImpl(
    private val weatherRepository: WeatherRepository
): GetWeatherPointsHistoryFlowUseCase {
    override suspend fun invoke(): Flow<List<WeatherPoint>> =
        weatherRepository.getWeatherPointsFlow()
}