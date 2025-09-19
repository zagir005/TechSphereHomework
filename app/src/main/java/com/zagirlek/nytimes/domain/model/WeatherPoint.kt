package com.zagirlek.nytimes.domain.model

data class WeatherPoint(
    val city: City,
    val temperature: Int,
    val id: Long
)
