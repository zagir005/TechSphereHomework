package com.zagirlek.local.computer.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zagirlek.local.computer.entity.ComputerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ComputerDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(computer: ComputerEntity): Long
    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(computer: ComputerEntity): Int
    @Query("DELETE FROM computers WHERE id = :id")
    suspend fun deleteById(id: Long): Int

    @Query("SELECT * FROM computers WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): ComputerEntity?
    @Query("SELECT * FROM computers WHERE id = :id")
    fun getByIdFlow(id: Long): Flow<ComputerEntity?>
    @Query("""
        SELECT * FROM computers
        WHERE id IN (:ids)
        ORDER BY code COLLATE NOCASE ASC
        """)
    suspend fun getByIdsList(ids: List<Long>): List<ComputerEntity>
    @Query("""
        SELECT * FROM computers
        WHERE id IN (:ids)
        ORDER BY code COLLATE NOCASE ASC
        """)
    suspend fun getByIdsFlow(ids: List<Long>): Flow<List<ComputerEntity>>

    @Query("""
        SELECT * FROM computers
        WHERE (:query IS NULL OR code LIKE '%' || :query || '%' COLLATE NOCASE)
        ORDER BY code COLLATE NOCASE ASC 
        """)
    fun searchList(query: String?): List<ComputerEntity>
    @Query("""
        SELECT * FROM computers
        WHERE (:query IS NULL OR code LIKE '%' || :query || '%' COLLATE NOCASE)
        ORDER BY code COLLATE NOCASE ASC 
        """)
    fun searchFlow(query: String?): Flow<List<ComputerEntity>>
}