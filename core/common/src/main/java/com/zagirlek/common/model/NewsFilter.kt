package com.zagirlek.common.model

data class NewsFilter(
    val category: NewsCategory? = null,
    val titleQuery: String? = null
)