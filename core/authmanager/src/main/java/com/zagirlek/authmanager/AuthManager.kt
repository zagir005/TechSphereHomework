package com.zagirlek.authmanager

import com.zagirlek.authmanager.mapper.toAuthToken
import com.zagirlek.authmanager.mapper.toUser
import com.zagirlek.authmanager.tokenmanager.TokenManager
import com.zagirlek.common.error.AuthError
import com.zagirlek.common.model.AuthToken
import com.zagirlek.common.model.User
import com.zagirlek.common.utils.runCatchingCancellable
import com.zagirlek.local.user.dao.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthManager (
    private val userDao: UserDao,
    private val authTokenManager: TokenManager
) {
    suspend fun getCurrAuthToken(): AuthToken? =
        authTokenManager.getToken()
            ?.let { userDao.getById(it)?.toAuthToken() }

    suspend fun login(
        nickname: String,
        password: String
    ): Result<AuthToken> = runCatchingCancellable{
        authTokenManager.clearToken()
        val user = userDao.findByLoginAndPassword(
            nickname = nickname,
            password = password
        )
        if (user == null)
            throw AuthError.InvalidCredentials
        authTokenManager.saveToken(user.id)
        user.toAuthToken()
    }

    suspend fun loginAsGuests(): AuthToken {
        authTokenManager.clearToken()
        authTokenManager.saveToken(AuthToken.GUEST_TOKEN.userId)
        return AuthToken.GUEST_TOKEN
    }

    suspend fun logout() =
        authTokenManager.clearToken()

    suspend fun getCurrUser(): User? =
        getCurrAuthToken()?.let {
            userDao.getById(id = it.userId)?.toUser()
        }

    suspend fun getCurrUserFlow(): Flow<User> {
        val token = getCurrAuthToken() ?: throw IllegalStateException(
            "Попытка получить текущего пользователя без авторизации"
        )
        return userDao.getByIdFlow(id = token.userId).map {
            requireNotNull(it){
                "Текущий пользователь не найден в базе"
            }.toUser()
        }
    }
}