package com.zagirlek.nytimes.core.networkchecker

interface NetworkConnectionChecker {
    fun checkConnection(): Boolean
}