package com.zagirlek.android.networkchecker

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

internal class NetworkConnectionCheckerImpl(
    private val context: Context
): NetworkConnectionChecker {
    override fun checkConnection(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.isInternetAvailable()
    }

    private fun ConnectivityManager.isInternetAvailable(): Boolean {
        return activeNetwork?.let { network ->
            getNetworkCapabilities(network)?.run {
                hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && (
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                        )
            }
        } ?: false
    }
}