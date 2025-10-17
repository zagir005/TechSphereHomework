package com.zagirlek.auth.usecase

import com.zagirlek.auth.model.AuthToken

fun interface AuthWithoutLoginUseCase {
    suspend operator fun invoke(): AuthToken
}