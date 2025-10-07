package com.zagirlek.nytimes.data.network.extractor.service

import com.zagirlek.nytimes.data.network.extractor.dto.ExtractDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ExtractorService {
    @GET("extract")
    suspend fun extractByUrl(
        @Query("url") link: String
    ): ExtractDTO

}