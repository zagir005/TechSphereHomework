package com.zagirlek.user.di

import com.zagirlek.local.user.dao.UserDao
import com.zagirlek.user.repository.UserRepository
import com.zagirlek.user.repository.UserRepositoryImpl

class UserDataModule(
    private val userDao: UserDao
) {
    val userRepository: UserRepository by lazy {
        UserRepositoryImpl(userDao)
    }
}