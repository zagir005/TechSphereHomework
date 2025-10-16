package com.zagirlek.remote.extractor.dto

import com.google.gson.annotations.SerializedName

data class ExtractedArticleDTO(
    @SerializedName("url") val link: String,
    @SerializedName("text") val text: String
)
