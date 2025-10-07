package com.zagirlek.nytimes.domain.model

import com.zagirlek.nytimes.core.model.NewsCategory
import java.time.LocalDateTime

data class ArticleFullWithStatus(
    val articleId: String = "",
    val link: String = "",
    val title: String = "",
    val fullText: String = "",
    val description: String? = "",
    val category: NewsCategory = NewsCategory.OTHER,
    val sourceName: String = "",
    val sourceIconUrl: String? = "",
    val creator: String? = "",
    val imageUrl: String? = "",
    val pubDate: LocalDateTime = LocalDateTime.now(),
    val isFavorite: Boolean = false,
    val isRead: Boolean = false
)
