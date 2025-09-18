package com.zagirlek.nytimes.data.mapper

import com.zagirlek.nytimes.core.local.entity.CityEntity
import com.zagirlek.nytimes.core.network.dto.CityDTO
import com.zagirlek.nytimes.domain.model.City

fun CityEntity.toDomain(): City = City(
    id = id,
    name = name
)

fun CityDTO.toDomain(): City = City(
    id = id,
    name = name
)

fun City.toData(): CityEntity = CityEntity(
    id = id,
    name = name
)