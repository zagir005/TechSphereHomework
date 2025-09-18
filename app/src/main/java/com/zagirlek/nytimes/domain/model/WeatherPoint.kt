package com.zagirlek.nytimes.domain.model

data class WeatherPoint(
    val city: City,
    val degrees: Int,
    val id: Long
)
