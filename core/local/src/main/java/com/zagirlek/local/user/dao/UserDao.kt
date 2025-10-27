package com.zagirlek.local.user.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import androidx.room.Update
import com.zagirlek.common.crud.CrudDao
import com.zagirlek.local.user.converters.UserConverter
import com.zagirlek.local.user.entitiy.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
@TypeConverters(
    UserConverter::class
)
interface UserDao: CrudDao<UserEntity> {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    override suspend fun insert(user: UserEntity): Long
    @Insert(onConflict = OnConflictStrategy.ABORT)
    override suspend fun insertAll(userList: List<UserEntity>): List<Long>
    @Update(onConflict = OnConflictStrategy.ABORT)
    override suspend fun update(user: UserEntity): Int
    @Query("DELETE FROM users WHERE id = :id")
    override suspend fun deleteById(id: Long): Int
    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    override suspend fun getById(id: Long): UserEntity?
    @Query("SELECT * FROM users WHERE id = :id")
    override fun getByIdFlow(id: Long): Flow<UserEntity?>
    @Query("""
        SELECT * FROM users
        WHERE (:query IS NULL OR nickname LIKE '%' || :query || '%' COLLATE NOCASE
               OR phone LIKE '%' || :query || '%' COLLATE NOCASE)
        ORDER BY nickname COLLATE NOCASE ASC """)
    override suspend fun getAllList(query: String?): List<UserEntity>
    @Query("""
        SELECT * FROM users
        WHERE (:query IS NULL OR nickname LIKE '%' || :query || '%' COLLATE NOCASE
               OR phone LIKE '%' || :query || '%' COLLATE NOCASE)
        ORDER BY nickname COLLATE NOCASE ASC """)
    override fun getAllFlow(query: String?): Flow<List<UserEntity>>
    @Query("""
        SELECT * FROM users
        WHERE id IN (:ids)
        ORDER BY nickname COLLATE NOCASE ASC
        """)
    override fun getByIdsFlow(ids: List<Long>): Flow<List<UserEntity>>
    @Query("""
        SELECT * FROM users
        WHERE id IN (:ids)
        ORDER BY nickname COLLATE NOCASE ASC
        """)
    override suspend fun getByIdsList(ids: List<Long>): List<UserEntity>
    @Query(
        """
        SELECT * FROM users
        WHERE (phone = :nickname OR nickname = :nickname)
          AND password = :password
        LIMIT 1
    """
    )
    suspend fun findByLoginAndPassword(nickname: String, password: String): UserEntity?
}