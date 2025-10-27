package com.zagirlek.common.crud

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class BaseCrudRepository<DM: CrudDomainModel, E: CrudEntity, DAO: CrudDao<E>>(
    private val crudDao: DAO
): CrudRepository<DM> {
    override suspend fun add(model: DM): Long = crudDao.insert(model.toEntity())
    override suspend fun update(model: DM): Int = crudDao.update(model.toEntity())
    override suspend fun deleteById(id: Long): Boolean = crudDao.deleteById(id) == 1
    override suspend fun getById(id: Long): DM? = crudDao.getById(id)?.toDomain()
    override fun getByIdFlow(id: Long): Flow<DM?> = crudDao.getByIdFlow(id).map { it?.toDomain() }
    override suspend fun getByIdsList(ids: List<Long>): List<DM> = crudDao.getByIdsList(ids).map { it.toDomain() }
    override fun getByIdsFlow(ids: List<Long>): Flow<List<DM>> = crudDao.getByIdsFlow(ids).map { list -> list.map { it.toDomain() } }
    override suspend fun getAllList(query: String?): List<DM> = crudDao.getAllList(query).map { it.toDomain() }
    override fun getAllFlow(query: String?): Flow<List<DM>> = crudDao.getAllFlow(query).map { list -> list.map { it.toDomain() } }

    abstract fun E.toDomain(): DM
    abstract fun DM.toEntity(): E
}
