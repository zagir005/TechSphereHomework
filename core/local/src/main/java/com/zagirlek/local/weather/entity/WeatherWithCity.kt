package com.zagirlek.local.weather.entity

import androidx.room.Embedded
import androidx.room.Relation

data class WeatherWithCity(
    @Embedded val weather: WeatherInfoEntity,
    @Relation(
        parentColumn = "cityId",
        entityColumn = "id"
    )
    val cityEntity: CityEntity
)