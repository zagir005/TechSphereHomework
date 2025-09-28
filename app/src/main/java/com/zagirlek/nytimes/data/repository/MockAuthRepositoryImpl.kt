package com.zagirlek.nytimes.data.repository

import com.zagirlek.nytimes.core.error.InvalidCredentialsError
import com.zagirlek.nytimes.core.error.NetworkError
import com.zagirlek.nytimes.core.error.ServerError
import com.zagirlek.nytimes.core.utils.runCatchingCancellable
import com.zagirlek.nytimes.core.networkchecker.NetworkConnectionChecker
import com.zagirlek.nytimes.domain.model.AuthToken
import com.zagirlek.nytimes.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlin.random.Random

class MockAuthRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker
): AuthRepository {
    override suspend fun getCurrToken(): AuthToken? {
        delay(1000)
        return null
    }

    override suspend fun login(
        login: String,
        password: String
    ): Result<AuthToken> = runCatchingCancellable{
        delay(1000)
        if (!networkConnectionChecker.checkConnection())
            throw NetworkError()
        else if (Random.nextBoolean())
            throw ServerError()
        else if (login == "Логин_Юзера" && password == "Пароль123")
            AuthToken.DEFAULT_TOKEN
        else
            throw InvalidCredentialsError()
    }

    override suspend fun getTokenWithoutLogin(): AuthToken {
        return AuthToken.DEFAULT_TOKEN
    }
}

