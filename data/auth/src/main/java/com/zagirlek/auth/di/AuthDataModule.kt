package com.zagirlek.auth.di

import com.zagirlek.android.tokenmanager.AuthTokenManager
import com.zagirlek.auth.repository.AuthRepository
import com.zagirlek.auth.repository.AuthRepositoryImpl
import com.zagirlek.local.user.dao.UserDao

class AuthDataModule(
    userDao: UserDao,
    authTokenManager: AuthTokenManager
) {
    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(
            userDao = userDao,
            authTokenManager = authTokenManager
        )
    }
}