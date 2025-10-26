package com.zagirlek.local.computer.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zagirlek.common.crud.CrudDao
import com.zagirlek.local.computer.entity.ComputerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ComputerDao: CrudDao<ComputerEntity> {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    override suspend fun insert(computer: ComputerEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    override suspend fun insertAll(computerList: List<ComputerEntity>): List<Long>

    @Update(onConflict = OnConflictStrategy.ABORT)
    override suspend fun update(computer: ComputerEntity): Int

    @Query("DELETE FROM computers WHERE id = :id")
    override suspend fun deleteById(id: Long): Int

    @Query("SELECT * FROM computers WHERE id = :id LIMIT 1")
    override suspend fun getById(id: Long): ComputerEntity?

    @Query("SELECT * FROM computers WHERE id = :id")
    override fun getByIdFlow(id: Long): Flow<ComputerEntity?>
    @Query("""
        SELECT * FROM computers
        WHERE id IN (:ids)
        ORDER BY code COLLATE NOCASE ASC
        """)
    override suspend fun getByIdsList(ids: List<Long>): List<ComputerEntity>

    @Query("""
        SELECT * FROM computers
        WHERE id IN (:ids)
        ORDER BY code COLLATE NOCASE ASC
        """)
    override fun getByIdsFlow(ids: List<Long>): Flow<List<ComputerEntity>>

    @Query("""
        SELECT * FROM computers
        WHERE (:query IS NULL OR code LIKE '%' || :query || '%' COLLATE NOCASE)
        ORDER BY code COLLATE NOCASE ASC 
        """)
    override suspend fun getAllList(query: String?): List<ComputerEntity>

    @Query("""
        SELECT * FROM computers
        WHERE (:query IS NULL OR code LIKE '%' || :query || '%' COLLATE NOCASE)
        ORDER BY code COLLATE NOCASE ASC 
        """)
    override fun getAllFlow(query: String?): Flow<List<ComputerEntity>>
}