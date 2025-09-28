package com.zagirlek.nytimes.data.usecase

import com.zagirlek.nytimes.domain.model.AuthToken
import com.zagirlek.nytimes.domain.repository.AuthRepository
import com.zagirlek.nytimes.domain.usecase.AuthUseCase

class AuthUseCaseImpl(
    private val authRepository: AuthRepository
): AuthUseCase {
    override suspend fun invoke(
        login: String,
        password: String
    ): Result<AuthToken> =
        authRepository.login(login = login, password = password)
}