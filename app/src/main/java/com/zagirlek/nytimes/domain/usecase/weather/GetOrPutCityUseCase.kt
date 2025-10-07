package com.zagirlek.nytimes.domain.usecase.weather

import com.zagirlek.nytimes.domain.model.City

fun interface GetOrPutCityUseCase {
    suspend operator fun invoke(name: String): Result<City>
}