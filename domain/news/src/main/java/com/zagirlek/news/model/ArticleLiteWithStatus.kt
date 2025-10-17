package com.zagirlek.news.model

import com.zagirlek.common.model.NewsCategory
import java.time.LocalDateTime

data class ArticleLiteWithStatus(
    val articleId: String,
    val link: String,
    val title: String,
    val description: String?,
    val category: NewsCategory,
    val sourceName: String,
    val sourceIconUrl: String?,
    val creator: String?,
    val imageUrl: String?,
    val pubDate: LocalDateTime,
    val isFavorite: Boolean,
    val isRead: Boolean
)
