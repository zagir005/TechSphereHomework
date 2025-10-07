package com.zagirlek.nytimes.domain.model

data class ArticleStatus(
    val articleId: String,
    val isRead: Boolean = false,
    val isFavorite: Boolean = false
)
