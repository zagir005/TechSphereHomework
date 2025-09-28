package com.zagirlek.nytimes.domain.usecase

import com.zagirlek.nytimes.domain.model.AuthToken

fun interface GetCurrentAuthTokenUseCase {
    suspend operator fun invoke(): AuthToken?
}