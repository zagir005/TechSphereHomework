package com.zagirlek.remote.extractor.service

import com.zagirlek.remote.extractor.dto.ExtractDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ExtractorService {
    @GET("extract")
    suspend fun extractByUrl(
        @Query("url") link: String
    ): ExtractDTO
}