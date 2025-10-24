package com.zagirlek.android

import android.content.Context
import com.zagirlek.android.networkchecker.NetworkConnectionChecker
import com.zagirlek.android.networkchecker.NetworkConnectionCheckerImpl
import com.zagirlek.android.tokenmanager.AuthTokenManager
import com.zagirlek.android.tokenmanager.tokenDataStore

object CoreAndroidModule {
    fun provideNetworkConnectionChecker(context: Context): NetworkConnectionChecker =
        NetworkConnectionCheckerImpl(context = context)

    fun provideAuthTokenManager(context: Context): AuthTokenManager =
        AuthTokenManager(tokenDataStore = context.tokenDataStore)
}
