package com.zagirlek.nytimes.data.network

import com.zagirlek.nytimes.BuildConfig
import com.zagirlek.nytimes.core.interceptor.APIKeyInterceptor
import com.zagirlek.nytimes.data.network.extractor.RemoteNewsExtractorSource
import com.zagirlek.nytimes.data.network.extractor.service.ExtractorService
import com.zagirlek.nytimes.data.network.news.RemoteNewsSource
import com.zagirlek.nytimes.data.network.news.service.NewsService
import com.zagirlek.nytimes.data.network.weather.service.AutocompleteService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object NetworkModule {
    private fun createOkHttpClient(vararg interceptors: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()

        interceptors.forEach { builder.addInterceptor(it) }

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(logging)

        return builder.build()
    }

    private fun buildRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val autocompleteService: AutocompleteService by lazy {
        buildRetrofit(
            baseUrl = BuildConfig.WEATHER_BASE_URL,
            client = createOkHttpClient(APIKeyInterceptor(apiKey = BuildConfig.WEATHER_API_KEY, keyParameterName = "key"))
        ).create(AutocompleteService::class.java)
    }

    private val newsService: NewsService by lazy {
        buildRetrofit(
            baseUrl = BuildConfig.NEWS_BASE_URL,
            client = createOkHttpClient(
                APIKeyInterceptor(
                    apiKey = listOf(
                        BuildConfig.NEWS_API_KEY,
                        BuildConfig.NEWS_API_KEY_1,
                        BuildConfig.NEWS_API_KEY_2,
                    ).random()
                )
            )
        ).create()
    }

    private val newsExtractorService: ExtractorService by lazy {
        buildRetrofit(
            baseUrl = BuildConfig.EXTRACTOR_BASE_URL,
            client = createOkHttpClient(
                APIKeyInterceptor(
                    apiKey = listOf(
                        BuildConfig.EXTRACTOR_API_KEY,
                        BuildConfig.EXTRACTOR_API_KEY_1,
                        BuildConfig.EXTRACTOR_API_KEY_2,
//                        BuildConfig.EXTRACTOR_API_KEY_3
                    ).random(),
                    keyParameterName = "api_token"
                )
            )
        ).create()
    }

    val remoteNewsSource: RemoteNewsSource by lazy {
        RemoteNewsSource(
            newsService
        )
    }

    val remoteNewsExtractorSource: RemoteNewsExtractorSource by lazy {
        RemoteNewsExtractorSource(newsExtractorService)
    }
}
