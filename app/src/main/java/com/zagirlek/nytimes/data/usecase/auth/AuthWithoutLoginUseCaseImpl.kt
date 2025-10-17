package com.zagirlek.nytimes.data.usecase.auth

import com.zagirlek.auth.model.AuthToken
import com.zagirlek.auth.repository.AuthRepository
import com.zagirlek.auth.usecase.AuthWithoutLoginUseCase

class AuthWithoutLoginUseCaseImpl(
    private val authRepository: AuthRepository
): AuthWithoutLoginUseCase {
    override suspend fun invoke(): AuthToken =
        authRepository.getTokenWithoutLogin()
}