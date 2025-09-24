package com.zagirlek.nytimes.domain.usecase

fun interface DeleteWeatherPointUseCase {
    suspend operator fun invoke(id: Long)
}