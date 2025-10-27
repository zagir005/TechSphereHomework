package com.zagirlek.computer.mapper

import com.zagirlek.common.model.Computer
import com.zagirlek.local.computer.entity.ComputerEntity

fun ComputerEntity.toDomain(): Computer = Computer(
    code = code,
    description = description,
    id = id
)

fun Computer.toEntity(): ComputerEntity = ComputerEntity(
    code = code,
    description = description,
    id = id
)
