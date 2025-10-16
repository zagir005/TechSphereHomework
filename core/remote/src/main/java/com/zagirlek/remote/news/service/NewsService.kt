package com.zagirlek.remote.news.service

import com.zagirlek.remote.news.dto.NewsPageDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("latest")
    suspend fun latest(
        @Query("category") category: List<String>? = null,
        @Query("page") page: String? = null,
        @Query("qInTitle") titleQuery: String? = null,
        @Query("id") id: String? = null,
        @Query("domain") domain: String? = null,
        @Query("language") language: String? = "en",
    ): NewsPageDTO
}