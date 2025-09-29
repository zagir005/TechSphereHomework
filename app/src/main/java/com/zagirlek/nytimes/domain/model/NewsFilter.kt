package com.zagirlek.nytimes.domain.model

data class NewsFilter(
    val category: List<NewsCategory> = emptyList(),
    val page: String = "",
    val titleQuery: String = "",
)
