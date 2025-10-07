package com.zagirlek.nytimes.domain.usecase.weather

import com.zagirlek.nytimes.domain.model.WeatherPoint
import kotlinx.coroutines.flow.Flow

fun interface GetWeatherPointsHistoryFlowUseCase {
    suspend operator fun invoke(): Flow<List<WeatherPoint>>
}