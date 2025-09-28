package com.zagirlek.nytimes.domain.usecase

import com.zagirlek.nytimes.domain.model.AuthToken

fun interface AuthUseCase {
    suspend operator fun invoke(login: String, password: String): Result<AuthToken>
}