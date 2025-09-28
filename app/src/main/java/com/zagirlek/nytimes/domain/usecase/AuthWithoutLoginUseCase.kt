package com.zagirlek.nytimes.domain.usecase

import com.zagirlek.nytimes.domain.model.AuthToken

fun interface AuthWithoutLoginUseCase {
    suspend operator fun invoke(): AuthToken
}