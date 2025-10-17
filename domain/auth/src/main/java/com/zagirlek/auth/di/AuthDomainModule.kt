package com.zagirlek.auth.di

import com.zagirlek.auth.repository.AuthRepository
import com.zagirlek.auth.usecase.AuthUseCase
import com.zagirlek.auth.usecase.AuthWithoutLoginUseCase
import com.zagirlek.auth.usecase.GetCurrentAuthTokenUseCase

class AuthDomainModule(
    private val authRepository: AuthRepository
) {
    fun provideAuthUseCase(): AuthUseCase =
        AuthUseCase(authRepository)

    fun provideAuthWithoutLoginUseCase(): AuthWithoutLoginUseCase =
        AuthWithoutLoginUseCase(authRepository)

    fun provideGetCurrentAuthTokenUseCase(): GetCurrentAuthTokenUseCase =
        GetCurrentAuthTokenUseCase(authRepository)
}