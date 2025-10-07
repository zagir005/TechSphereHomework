package com.zagirlek.nytimes.data.usecase.auth

import com.zagirlek.nytimes.domain.model.AuthToken
import com.zagirlek.nytimes.domain.repository.AuthRepository
import com.zagirlek.nytimes.domain.usecase.auth.AuthWithoutLoginUseCase

class AuthWithoutLoginUseCaseImpl(
    private val authRepository: AuthRepository
): AuthWithoutLoginUseCase {
    override suspend fun invoke(): AuthToken =
        authRepository.getTokenWithoutLogin()
}