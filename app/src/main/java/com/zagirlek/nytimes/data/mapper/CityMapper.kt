package com.zagirlek.nytimes.data.mapper

import com.zagirlek.local.weather.entity.CityEntity

import com.zagirlek.remote.autocomplete.dto.CityDTO
import com.zagirlek.weather.model.City

fun CityEntity.toDomain(): City = City(
    id = id,
    name = name
)

fun CityDTO.toDomain(): City = City(
    id = id,
    name = name
)