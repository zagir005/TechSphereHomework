package com.zagirlek.nytimes.data.usecase.auth

import com.zagirlek.auth.model.AuthToken
import com.zagirlek.auth.repository.AuthRepository
import com.zagirlek.auth.usecase.GetCurrentAuthTokenUseCase

class GetCurrentAuthTokenUseCaseImpl(
    private val authRepository: AuthRepository
): GetCurrentAuthTokenUseCase {
    override suspend fun invoke(): AuthToken? = authRepository.getCurrToken()
}