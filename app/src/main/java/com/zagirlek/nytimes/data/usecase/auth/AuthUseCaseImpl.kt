package com.zagirlek.nytimes.data.usecase.auth

import com.zagirlek.auth.model.AuthToken
import com.zagirlek.auth.repository.AuthRepository
import com.zagirlek.auth.usecase.AuthUseCase

class AuthUseCaseImpl(
    private val authRepository: AuthRepository
): AuthUseCase {
    override suspend fun invoke(
        login: String,
        password: String
    ): Result<AuthToken> =
        authRepository.login(login = login, password = password)
}