package com.zagirlek.nytimes.data.mapper

import com.zagirlek.nytimes.core.local.entity.WeatherWithCity
import com.zagirlek.nytimes.domain.model.WeatherPoint

fun WeatherWithCity.toDomain(): WeatherPoint = WeatherPoint(
    city = cityEntity.toDomain(),
    temperature = weather.degree,
    id = weather.id
)

fun List<WeatherWithCity>.toDomain(): List<WeatherPoint> = map {
    it.toDomain()
}