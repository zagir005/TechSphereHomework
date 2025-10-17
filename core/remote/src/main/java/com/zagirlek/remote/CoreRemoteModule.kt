package com.zagirlek.remote

import com.zagirlek.remote.autocomplete.service.AutocompleteService
import com.zagirlek.remote.extractor.RemoteNewsExtractorSource
import com.zagirlek.remote.extractor.RemoteNewsExtractorSourceImpl
import com.zagirlek.remote.extractor.service.ExtractorService
import com.zagirlek.remote.interceptor.APIKeyInterceptor
import com.zagirlek.remote.news.RemoteNewsSource
import com.zagirlek.remote.news.RemoteNewsSourceImpl
import com.zagirlek.remote.news.service.NewsService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class CoreRemoteModule(
    private val autocompleteBaseUrl: String,
    private val extractorBaseUrl: String,
    private val newsBaseUrl: String,
    private val newsDomains: String,
    private val newsKey: List<String>,
    private val extractorKey: List<String>,
    private val autocompleteKey: List<String>,
) {
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

    private fun createAutocompleteService(): AutocompleteService = buildRetrofit(
            baseUrl = autocompleteBaseUrl,
            client = createOkHttpClient(
                APIKeyInterceptor(
                    apiKey = autocompleteKey.random(),
                    keyParameterName = "key"
                )
            )
        ).create(AutocompleteService::class.java)


    private fun createNewsService(): NewsService =
        buildRetrofit(
            baseUrl = newsBaseUrl,
            client = createOkHttpClient(
                APIKeyInterceptor(
                    apiKey = newsKey.random()
                )
            )
        ).create()


    private fun createNewsExtractorService(): ExtractorService =
        buildRetrofit(
            baseUrl = extractorBaseUrl,
            client = createOkHttpClient(
                APIKeyInterceptor(
                    apiKey = extractorKey.random(),
                    keyParameterName = "api_token"
                )
            )
        ).create()


    val remoteNewsSource: RemoteNewsSource by lazy {
        RemoteNewsSourceImpl(
            newsService = createNewsService(),
            newsDomains = newsDomains
        )
    }

    val remoteNewsExtractorSource: RemoteNewsExtractorSource by lazy {
        RemoteNewsExtractorSourceImpl(createNewsExtractorService())
    }

    val autocompleteService: AutocompleteService by lazy {
        createAutocompleteService()
    }
}
