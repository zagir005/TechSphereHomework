package com.zagirlek.auth.usecase

import com.zagirlek.auth.model.AuthToken

fun interface AuthUseCase {
    suspend operator fun invoke(login: String, password: String): Result<AuthToken>
}