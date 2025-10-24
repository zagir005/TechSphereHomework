package com.zagirlek.auth.usecase

import com.zagirlek.auth.model.AuthToken
import com.zagirlek.auth.repository.AuthRepository

class AuthWithoutLoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): AuthToken =
        authRepository.loginAsGuests()
}