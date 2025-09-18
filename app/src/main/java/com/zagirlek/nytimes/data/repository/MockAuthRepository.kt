package com.zagirlek.nytimes.data.repository

import com.zagirlek.nytimes.domain.model.AuthToken
import com.zagirlek.nytimes.domain.repository.AuthRepository
import kotlinx.coroutines.delay

class MockAuthRepository(): AuthRepository {

    override suspend fun getCurrToken(): AuthToken? {
        delay(2000)
        return null
    }

    override suspend fun login(
        login: String,
        password: String
    ): AuthToken {
        delay(2000)
        return AuthToken.DEFAULT_TOKEN
    }

    override suspend fun getTokenWithoutLogin(): AuthToken {
        return AuthToken.DEFAULT_TOKEN
    }

}