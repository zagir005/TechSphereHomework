package com.zagirlek.auth.usecase

import com.zagirlek.authmanager.AuthManager
import com.zagirlek.common.model.AuthToken

class AuthWithoutLoginUseCase(
    private val authManager: AuthManager
) {
    suspend operator fun invoke(): AuthToken =
        authManager.loginAsGuests()
}