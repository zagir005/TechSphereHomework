package com.zagirlek.weather.usecase

import com.zagirlek.weather.model.City

fun interface GetOrPutCityUseCase {
    suspend operator fun invoke(name: String): Result<City>
}