package com.zagirlek.nytimes.data.usecase.auth

import com.zagirlek.nytimes.domain.model.AuthToken
import com.zagirlek.nytimes.domain.repository.AuthRepository
import com.zagirlek.nytimes.domain.usecase.auth.GetCurrentAuthTokenUseCase

class GetCurrentAuthTokenUseCaseImpl(
    private val authRepository: AuthRepository
): GetCurrentAuthTokenUseCase {
    override suspend fun invoke(): AuthToken? = authRepository.getCurrToken()
}