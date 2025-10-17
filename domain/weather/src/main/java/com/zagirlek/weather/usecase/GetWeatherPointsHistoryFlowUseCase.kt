package com.zagirlek.weather.usecase

import com.zagirlek.weather.model.WeatherPoint
import kotlinx.coroutines.flow.Flow

fun interface GetWeatherPointsHistoryFlowUseCase {
    suspend operator fun invoke(): Flow<List<WeatherPoint>>
}