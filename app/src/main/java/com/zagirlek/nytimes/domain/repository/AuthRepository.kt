package com.zagirlek.nytimes.domain.repository

import com.zagirlek.nytimes.domain.model.AuthToken

interface AuthRepository {
    suspend fun getCurrToken(): AuthToken?
    suspend fun login(login: String, password: String): AuthToken
    suspend fun getTokenWithoutLogin(): AuthToken
}