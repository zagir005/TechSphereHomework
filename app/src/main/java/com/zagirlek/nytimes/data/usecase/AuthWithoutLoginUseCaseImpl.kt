package com.zagirlek.nytimes.data.usecase

import com.zagirlek.nytimes.domain.model.AuthToken
import com.zagirlek.nytimes.domain.repository.AuthRepository
import com.zagirlek.nytimes.domain.usecase.AuthWithoutLoginUseCase

class AuthWithoutLoginUseCaseImpl(
    private val authRepository: AuthRepository
): AuthWithoutLoginUseCase {
    override suspend fun invoke(): AuthToken =
        authRepository.getTokenWithoutLogin()
}