package com.zagirlek.nytimes.data.mapper

import com.zagirlek.nytimes.data.local.weather.entity.WeatherWithCity
import com.zagirlek.nytimes.domain.model.WeatherPoint

fun WeatherWithCity.toDomain(): WeatherPoint = WeatherPoint(
    city = cityEntity.toDomain(),
    temperature = weather.temperature,
    id = weather.id
)

fun List<WeatherWithCity>.toDomain(): List<WeatherPoint> = map {
    it.toDomain()
}