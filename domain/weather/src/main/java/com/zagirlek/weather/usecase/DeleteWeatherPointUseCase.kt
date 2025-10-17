package com.zagirlek.weather.usecase

fun interface DeleteWeatherPointUseCase {
    suspend operator fun invoke(id: Long)
}