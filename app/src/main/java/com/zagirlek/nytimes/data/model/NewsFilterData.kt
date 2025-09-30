package com.zagirlek.nytimes.data.model

data class NewsFilterData(
    val category: NewsCategoryData? = null,
    val titleQuery: String = "",
)