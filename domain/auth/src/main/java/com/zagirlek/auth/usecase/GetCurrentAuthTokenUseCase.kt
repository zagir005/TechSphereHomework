package com.zagirlek.auth.usecase

import com.zagirlek.auth.model.AuthToken
import com.zagirlek.auth.repository.AuthRepository

class GetCurrentAuthTokenUseCase(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(): AuthToken? =
        authRepository.getCurrToken()
}