package com.zagirlek.user.repository

import com.zagirlek.common.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun add(user: User): Long
    suspend fun update(user: User): Int
    suspend fun deleteById(id: Long): Int
    fun getAllFlow(): Flow<List<User>>
    suspend fun getById(id: Long): User?
    suspend fun getByLoginAndPassword(login: String, password: String): User?
    fun searchUsersFlow(query: String?): Flow<List<User>>
    suspend fun searchUsersList(query: String?): List<User>
}