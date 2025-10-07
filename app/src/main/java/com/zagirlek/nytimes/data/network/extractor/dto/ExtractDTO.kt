package com.zagirlek.nytimes.data.network.extractor.dto

import com.google.gson.annotations.SerializedName

data class ExtractDTO(
    @SerializedName("data") val article: ExtractedArticleDTO
)
