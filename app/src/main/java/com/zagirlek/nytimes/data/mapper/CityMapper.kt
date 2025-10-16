package com.zagirlek.nytimes.data.mapper

import com.zagirlek.local.weather.entity.CityEntity
import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.remote.autocomplete.dto.CityDTO

fun CityEntity.toDomain(): City = City(
    id = id,
    name = name
)

fun CityDTO.toDomain(): City = City(
    id = id,
    name = name
)