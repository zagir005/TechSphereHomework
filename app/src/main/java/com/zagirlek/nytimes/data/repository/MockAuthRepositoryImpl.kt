package com.zagirlek.nytimes.data.repository

import com.zagirlek.android.networkchecker.NetworkConnectionChecker
import com.zagirlek.auth.model.AuthToken
import com.zagirlek.auth.repository.AuthRepository
import com.zagirlek.common.error.AuthError
import com.zagirlek.common.utils.runCatchingCancellable
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
            throw AuthError.NoNetworkConnection
        else if (Random.nextBoolean())
            throw AuthError.ServerError
        else if (login == "Логин_Юзера" && password == "Пароль123")
            AuthToken.DEFAULT_TOKEN
        else
            throw AuthError.InvalidCredentials
    }

    override suspend fun getTokenWithoutLogin(): AuthToken {
        return AuthToken.DEFAULT_TOKEN
    }
}

