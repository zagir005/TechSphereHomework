package com.zagirlek.auth.usecase

import com.zagirlek.authmanager.AuthManager
import com.zagirlek.common.model.User

class GetCurrentUserUseCase(
    private val authRepository: AuthManager
) {
    suspend operator fun invoke(): User? =
        authRepository.getCurrUser()
}