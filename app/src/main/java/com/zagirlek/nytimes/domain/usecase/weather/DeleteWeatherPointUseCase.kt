package com.zagirlek.nytimes.domain.usecase.weather

fun interface DeleteWeatherPointUseCase {
    suspend operator fun invoke(id: Long)
}