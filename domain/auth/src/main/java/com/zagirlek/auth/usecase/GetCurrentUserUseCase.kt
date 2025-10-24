package com.zagirlek.auth.usecase

import com.zagirlek.auth.repository.AuthRepository
import com.zagirlek.common.model.User

class GetCurrentUserUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): User? =
        authRepository.getCurrUser()
}