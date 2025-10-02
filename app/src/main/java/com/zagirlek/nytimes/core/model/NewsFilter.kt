package com.zagirlek.nytimes.core.model

data class NewsFilter(
    val category: NewsCategory? = null,
    val titleQuery: String? = null,
    val isRead: Boolean = false,
    val isFavorite: Boolean = false
)