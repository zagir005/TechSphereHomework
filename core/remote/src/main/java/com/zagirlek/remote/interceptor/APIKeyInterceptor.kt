package com.zagirlek.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

internal class APIKeyInterceptor(val apiKey: String, val keyParameterName: String = "apiKey"): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
        val urlWithKey = url.newBuilder().addQueryParameter(keyParameterName, apiKey).build()
        val currentRequest = chain.request().newBuilder()
        val newRequest = currentRequest.url(urlWithKey).build()

        return chain.proceed(newRequest)
    }
}