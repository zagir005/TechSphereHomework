package com.zagirlek.auth.di

import com.zagirlek.android.networkchecker.NetworkConnectionChecker
import com.zagirlek.auth.repository.AuthRepository
import com.zagirlek.auth.repository.MockAuthRepositoryImpl

class AuthDataModule(
    networkConnectionChecker: NetworkConnectionChecker
) {
    val authRepository: AuthRepository by lazy {
        MockAuthRepositoryImpl(
            networkConnectionChecker
        )
    }
}