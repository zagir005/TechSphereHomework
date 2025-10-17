package com.zagirlek.auth.repository

import com.zagirlek.auth.model.AuthToken

interface AuthRepository {
    suspend fun getCurrToken(): AuthToken?
    suspend fun login(login: String, password: String): Result<AuthToken>
    suspend fun getTokenWithoutLogin(): AuthToken
}