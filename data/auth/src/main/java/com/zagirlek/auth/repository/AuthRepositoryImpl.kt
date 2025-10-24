package com.zagirlek.auth.repository

import com.zagirlek.android.tokenmanager.AuthTokenManager
import com.zagirlek.auth.mapper.toAuthToken
import com.zagirlek.auth.model.AuthToken
import com.zagirlek.common.error.AuthError
import com.zagirlek.common.utils.runCatchingCancellable
import com.zagirlek.local.user.dao.UserDao

class AuthRepositoryImpl(
    private val userDao: UserDao,
    private val authTokenManager: AuthTokenManager
): AuthRepository {
    override suspend fun getCurrAuthToken(): AuthToken? =
        authTokenManager.getToken()
            ?.let { userDao.getById(it)?.toAuthToken() }

    override suspend fun login(
        nickname: String,
        password: String
    ): Result<AuthToken> = runCatchingCancellable{
        val user = userDao.findByLoginAndPassword(
            nickname = nickname,
            password = password
        )
        if (user == null)
            throw AuthError.InvalidCredentials
        authTokenManager.saveToken(user.id)
        user.toAuthToken()
    }

    override suspend fun loginAsGuests(): AuthToken {
        authTokenManager.clearToken()
        authTokenManager.saveToken(AuthToken.GUEST_TOKEN.userId)
        return AuthToken.GUEST_TOKEN
    }

    override suspend fun logout() =
        authTokenManager.clearToken()
}