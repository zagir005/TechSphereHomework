package com.zagirlek.nytimes.data.network

import com.zagirlek.nytimes.BuildConfig
import com.zagirlek.nytimes.core.interceptor.APIKeyInterceptor
import com.zagirlek.nytimes.data.network.weather.service.AutocompleteService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private fun createOkHttpClient(vararg interceptors: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()

        interceptors.forEach { builder.addInterceptor(it) }

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(logging)

        return builder.build()
    }

    private fun createRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val autocompleteApi: AutocompleteService by lazy {
        createRetrofit(
            baseUrl = BuildConfig.WEATHER_BASE_URL,
            client = createOkHttpClient(APIKeyInterceptor(apiKey = BuildConfig.WEATHER_API_KEY, keyParameterName = "key"))
        )
            .create(AutocompleteService::class.java)
    }

    val newsApi: Retrofit by lazy {
        createRetrofit(
            baseUrl = BuildConfig.NEWS_BASE_URL,
            client = createOkHttpClient(APIKeyInterceptor(apiKey = BuildConfig.NEWS_API_KEY))
        )
    }

    val newsExtractorApi: Retrofit by lazy {
        createRetrofit(
            baseUrl = BuildConfig.EXTRACTOR_BASE_URL,
            client = createOkHttpClient(APIKeyInterceptor(apiKey = BuildConfig.EXTRACTOR_BASE_URL, keyParameterName = "api_token"))
        )
    }
}
