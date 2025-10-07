package com.zagirlek.nytimes.data.network.news.dto

import com.google.gson.annotations.SerializedName

data class NewsPageDTO(
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("results") val newsList: List<ArticleDTO>,
    @SerializedName("nextPage") val nextPage: String?
)
