package com.zagirlek.common.crud

import kotlinx.coroutines.flow.Flow

interface CrudDao<E: CrudEntity> {
    suspend fun insert(entity: E): Long
    suspend fun insertAll(entity: List<E>): List<Long>
    suspend fun update(entity: E): Int
    suspend fun deleteById(id: Long): Int
    suspend fun getById(id: Long): E?
    fun getByIdFlow(id: Long): Flow<E?>
    suspend fun getByIdsList(ids: List<Long>): List<E>
    fun getByIdsFlow(ids: List<Long>): Flow<List<E>>
    suspend fun getAllList(query: String? = null): List<E>
    fun getAllFlow(query: String? = null): Flow<List<E>>
}