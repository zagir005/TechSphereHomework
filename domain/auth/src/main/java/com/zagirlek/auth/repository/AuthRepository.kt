package com.zagirlek.auth.repository

import com.zagirlek.auth.model.AuthToken
import com.zagirlek.common.model.User

interface AuthRepository {
    suspend fun getCurrAuthToken(): AuthToken?
    suspend fun login(nickname: String, password: String): Result<AuthToken>
    suspend fun loginAsGuests(): AuthToken
    suspend fun logout()

    suspend fun getCurrUser(): User?
}