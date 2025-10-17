package com.zagirlek.weather.usecase

import com.zagirlek.weather.model.City

fun interface GetRecentCityListUseCase {
    suspend operator fun invoke(name: String): List<City>
}