package com.zagirlek.auth.usecase

import com.zagirlek.authmanager.AuthManager
import com.zagirlek.common.model.AuthToken

class AuthUseCase(
    private val authManager: AuthManager
) {
    suspend operator fun invoke(
        login: String,
        password: String
    ): Result<AuthToken> =
        authManager.login(nickname = login, password = password)
}