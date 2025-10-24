package com.zagirlek.auth.di

import com.zagirlek.auth.usecase.AuthUseCase
import com.zagirlek.auth.usecase.AuthWithoutLoginUseCase
import com.zagirlek.auth.usecase.GetCurrentAuthTokenUseCase
import com.zagirlek.auth.usecase.GetCurrentUserFlowUseCase
import com.zagirlek.auth.usecase.GetCurrentUserUseCase
import com.zagirlek.auth.usecase.LogoutUseCase
import com.zagirlek.authmanager.AuthManager

class AuthDomainModule(
    private val authManager: AuthManager
) {
    fun provideAuthUseCase(): AuthUseCase =
        AuthUseCase(authManager)
    fun provideAuthWithoutLoginUseCase(): AuthWithoutLoginUseCase =
        AuthWithoutLoginUseCase(authManager)
    fun provideGetCurrentAuthTokenUseCase(): GetCurrentAuthTokenUseCase =
        GetCurrentAuthTokenUseCase(authManager)
    fun provideGetCurrentUserUseCase(): GetCurrentUserUseCase =
        GetCurrentUserUseCase(authManager)
    fun provideLogoutUseCase(): LogoutUseCase =
        LogoutUseCase(authManager)
    fun getCurrentUserFlowUseCase(): GetCurrentUserFlowUseCase =
        GetCurrentUserFlowUseCase(authManager)
}