package com.zagirlek.auth.di

import com.zagirlek.auth.repository.AuthRepository
import com.zagirlek.auth.usecase.AuthUseCase
import com.zagirlek.auth.usecase.AuthWithoutLoginUseCase
import com.zagirlek.auth.usecase.GetCurrentAuthTokenUseCase
import com.zagirlek.auth.usecase.GetCurrentUserUseCase
import com.zagirlek.auth.usecase.LogoutUseCase

class AuthDomainModule(
    private val authRepository: AuthRepository
) {
    fun provideAuthUseCase(): AuthUseCase =
        AuthUseCase(authRepository)
    fun provideAuthWithoutLoginUseCase(): AuthWithoutLoginUseCase =
        AuthWithoutLoginUseCase(authRepository)
    fun provideGetCurrentAuthTokenUseCase(): GetCurrentAuthTokenUseCase =
        GetCurrentAuthTokenUseCase(authRepository)
    fun provideGetCurrentUserUseCase(): GetCurrentUserUseCase =
        GetCurrentUserUseCase(authRepository)
    fun provideLogoutUseCase(): LogoutUseCase =
        LogoutUseCase(authRepository)
}