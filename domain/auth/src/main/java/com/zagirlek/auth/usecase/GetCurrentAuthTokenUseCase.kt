package com.zagirlek.auth.usecase

import com.zagirlek.auth.model.AuthToken

fun interface GetCurrentAuthTokenUseCase {
    suspend operator fun invoke(): AuthToken?
}