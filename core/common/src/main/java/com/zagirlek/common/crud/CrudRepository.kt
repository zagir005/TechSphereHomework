package com.zagirlek.common.crud

import kotlinx.coroutines.flow.Flow

interface CrudRepository<DM: CrudDomainModel> {
    suspend fun add(model: DM): Long
    suspend fun update(model: DM): Int
    suspend fun deleteById(id: Long): Boolean
    suspend fun getById(id: Long): DM?
    fun getByIdFlow(id: Long): Flow<DM?>
    suspend fun getByIdsList(ids: List<Long>): List<DM>
    fun getByIdsFlow(ids: List<Long>): Flow<List<DM>>
    suspend fun getAllList(query: String? = null): List<DM>
    fun getAllFlow(query: String? = null): Flow<List<DM>>
}