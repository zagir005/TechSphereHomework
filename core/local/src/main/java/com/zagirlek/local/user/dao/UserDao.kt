package com.zagirlek.local.user.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import androidx.room.Update
import com.zagirlek.local.user.converters.UserConverter
import com.zagirlek.local.user.entitiy.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
@TypeConverters(
    UserConverter::class
)
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: UserEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(userList: List<UserEntity>)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(user: UserEntity): Int

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteById(id: Long): Int

    @Query("SELECT * FROM users ORDER BY nickname COLLATE NOCASE ASC")
    fun getAllFlow(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users ORDER BY nickname COLLATE NOCASE ASC")
    suspend fun getAllList(): List<UserEntity>

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): UserEntity?

    @Query("""
        SELECT * FROM users
        WHERE (phone = :login OR nickname = :login)
          AND password = :password
        LIMIT 1
    """)
    suspend fun findByLoginAndPassword(login: String, password: String): UserEntity?

    @Query("""
        SELECT * FROM users
        WHERE (:query IS NULL OR nickname LIKE '%' || :query || '%' COLLATE NOCASE
               OR phone LIKE '%' || :query || '%' COLLATE NOCASE)
        ORDER BY nickname COLLATE NOCASE ASC """)
    fun searchUsersFlow(query: String?): Flow<List<UserEntity>>

    @Query("""
        SELECT * FROM users
        WHERE (:query IS NULL OR nickname LIKE '%' || :query || '%' COLLATE NOCASE
               OR phone LIKE '%' || :query || '%' COLLATE NOCASE)
        ORDER BY nickname COLLATE NOCASE ASC """)
    fun searchUsersList(query: String?): List<UserEntity>
}