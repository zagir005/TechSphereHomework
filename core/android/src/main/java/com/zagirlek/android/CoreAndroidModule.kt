package com.zagirlek.android

import android.content.Context
import com.zagirlek.android.networkchecker.NetworkConnectionChecker
import com.zagirlek.android.networkchecker.NetworkConnectionCheckerImpl

object CoreAndroidModule {
    fun provideNetworkConnectionChecker(context: Context): NetworkConnectionChecker =
        NetworkConnectionCheckerImpl(context)
}