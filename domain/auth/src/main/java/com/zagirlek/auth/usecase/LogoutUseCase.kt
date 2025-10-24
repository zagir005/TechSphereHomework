package com.zagirlek.auth.usecase

import com.zagirlek.authmanager.AuthManager

class LogoutUseCase(
    private val authRepository: AuthManager
) {
    suspend operator fun invoke() =
        authRepository.logout()
}