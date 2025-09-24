package com.zagirlek.nytimes.core.interceptor

import com.zagirlek.nytimes.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class APIKeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
        val urlWithKey = url.newBuilder().addQueryParameter("key", BuildConfig.API_KEY).build()
        val currentRequest = chain.request().newBuilder()
        val newRequest = currentRequest.url(urlWithKey).build()

        return chain.proceed(newRequest)
    }
}