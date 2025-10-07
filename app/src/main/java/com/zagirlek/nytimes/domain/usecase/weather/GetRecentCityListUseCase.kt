package com.zagirlek.nytimes.domain.usecase.weather

import com.zagirlek.nytimes.domain.model.City

fun interface GetRecentCityListUseCase {
    suspend operator fun invoke(name: String): List<City>
}