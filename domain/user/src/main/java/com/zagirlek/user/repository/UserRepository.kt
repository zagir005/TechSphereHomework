package com.zagirlek.user.repository

import com.zagirlek.common.crud.CrudRepository
import com.zagirlek.common.model.User

interface UserRepository: CrudRepository<User> {
    suspend fun getByLoginAndPassword(login: String, password: String): User?
}