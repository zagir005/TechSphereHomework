package com.zagirlek.user.repository

import com.zagirlek.common.crud.BaseCrudRepository
import com.zagirlek.common.model.User
import com.zagirlek.local.user.dao.UserDao
import com.zagirlek.local.user.entitiy.UserEntity

class UserRepositoryImpl(
    private val userDao: UserDao,
): UserRepository, BaseCrudRepository<User, UserEntity, UserDao>(crudDao = userDao) {

    override suspend fun getByLoginAndPassword(
        login: String,
        password: String
    ): User? = userDao.findByLoginAndPassword(
        nickname = login,
        password = password
    )?.toDomain()

    override fun UserEntity.toDomain(): User = User(
        id = id,
        phone = phone,
        nickname = nickname,
        status = type,
        password = password
    )

    override fun User.toEntity(): UserEntity = UserEntity(
        id = id,
        phone = phone,
        nickname = nickname,
        type = status,
        password = password
    )
}