package com.zagirlek.nytimes.data.usecase

import com.zagirlek.nytimes.domain.model.WeatherPoint
import com.zagirlek.nytimes.domain.repository.WeatherRepository
import com.zagirlek.nytimes.domain.usecase.GetWeatherPointsHistoryFlowUseCase
import kotlinx.coroutines.flow.Flow

class GetWeatherPointsHistoryFlowUseCaseImpl(
    private val weatherRepository: WeatherRepository
): GetWeatherPointsHistoryFlowUseCase  {
    override suspend fun invoke(): Flow<List<WeatherPoint>> =
        weatherRepository.getWeatherPointsFlow()
}