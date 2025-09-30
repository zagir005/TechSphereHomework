package com.zagirlek.nytimes.domain.model

import com.zagirlek.nytimes.core.model.NewsCategory
import java.time.LocalDateTime

data class ArticleFull(
    val articleId: String,
    val link: String,
    val title: String,
    val description: String,
    val fullText: String,
    val category: NewsCategory,
    val sourceName: String,
    val sourceIconUrl: String,
    val creator: List<String>,
    val imageUrl: String,
    val pubDate: LocalDateTime
)
