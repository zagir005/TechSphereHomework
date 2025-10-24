package com.zagirlek.auth.usecase

import com.zagirlek.auth.model.AuthToken
import com.zagirlek.auth.repository.AuthRepository

class AuthUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        login: String,
        password: String
    ): Result<AuthToken> =
        authRepository.login(nickname = login, password = password)
}