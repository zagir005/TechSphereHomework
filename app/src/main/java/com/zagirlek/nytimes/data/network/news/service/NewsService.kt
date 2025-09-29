package com.zagirlek.nytimes.data.network.news.service

import com.zagirlek.nytimes.data.network.news.dto.NewsPageDTO
import com.zagirlek.nytimes.domain.model.NewsCategory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("latest")
    suspend fun latest(
        @Query("domain") domain: String? = null,
        @Query("category") category: List<NewsCategory>? = null,
        @Query("page") page: String? = null,
        @Query("qInTitle") titleQuery: String? = null,
        @Query("language") language: String = "en",
        @Query("id") id: String? = null
    ): NewsPageDTO
}