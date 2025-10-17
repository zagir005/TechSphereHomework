package com.zagirlek.remote.extractor.dto

import com.google.gson.annotations.SerializedName

data class ExtractDTO(
    @SerializedName("data") val article: ExtractedArticleDTO
)
