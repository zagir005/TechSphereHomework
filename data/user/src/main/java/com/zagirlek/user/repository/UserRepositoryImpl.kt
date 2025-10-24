package com.zagirlek.user.repository

import com.zagirlek.local.user.dao.UserDao
import com.zagirlek.user.mapper.toDomain
import com.zagirlek.user.mapper.toEntity
import com.zagirlek.user.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userDao: UserDao
): UserRepository {
    override suspend fun add(user: User): Long = userDao.insert(user.toEntity())

    override suspend fun update(user: User): Int = userDao.update(user.toEntity())

    override suspend fun deleteById(id: Long): Int = userDao.deleteById(id)

    override fun getAllFlow(): Flow<List<User>> = userDao.getAllFlow().map { it.toDomain() }

    override suspend fun getById(id: Long): User? = userDao.getById(id)?.toDomain()

    override suspend fun getByLoginAndPassword(
        login: String,
        password: String
    ): User? = userDao.findByLoginAndPassword(
        nickname = login,
        password = password
    )?.toDomain()

    override fun searchUsersFlow(query: String?): Flow<List<User>> =
        userDao.searchUsersFlow(query).map { it.toDomain() }

    override suspend fun searchUsersList(query: String?): List<User> =
        userDao.searchUsersList(query).map { it.toDomain() }
}