package com.zagirlek.authmanager.di

import android.content.Context
import com.zagirlek.authmanager.AuthManager
import com.zagirlek.authmanager.tokenmanager.TokenManager
import com.zagirlek.authmanager.tokenmanager.tokenDataStore
import com.zagirlek.local.user.dao.UserDao

class AuthManagerModule(
    private val userDao: UserDao
){
    private fun getTokenManager(context: Context): TokenManager = TokenManager(
        tokenDataStore = context.tokenDataStore
    )
    fun getAuthManager(context: Context): AuthManager = AuthManager(
        userDao = userDao,
        authTokenManager = getTokenManager(context)
    )
}