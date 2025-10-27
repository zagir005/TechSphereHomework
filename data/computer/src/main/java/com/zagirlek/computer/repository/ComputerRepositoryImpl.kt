package com.zagirlek.computer.repository

import com.zagirlek.common.crud.BaseCrudRepository
import com.zagirlek.common.model.Computer
import com.zagirlek.local.computer.dao.ComputerDao
import com.zagirlek.local.computer.entity.ComputerEntity


class ComputerRepositoryImpl(
    computerDao: ComputerDao
): BaseCrudRepository<Computer, ComputerEntity, ComputerDao>(
    crudDao = computerDao
) {
    override fun ComputerEntity.toDomainSingle(): Computer = Computer(
        id = id,
        code = code,
        description = description
    )

    override fun Computer.toEntity(): ComputerEntity = ComputerEntity(
        id = id,
        code = code,
        description = description
    )
}