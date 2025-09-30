package com.zagirlek.nytimes.domain.model

data class NewsFilter(
    val category: NewsCategory? = null,
    val titleQuery: String = "",
)