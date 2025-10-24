package com.zagirlek.auth.usecase

import com.zagirlek.auth.repository.AuthRepository

class LogoutUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() =
        authRepository.logout()

}