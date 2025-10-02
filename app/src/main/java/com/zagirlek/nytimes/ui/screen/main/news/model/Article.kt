package com.zagirlek.nytimes.ui.screen.main.news.model

import com.zagirlek.nytimes.core.model.NewsCategory
import java.time.LocalDateTime

data class Article(
    val articleId: String = "",
    val link: String = "",
    val title: String = "Lorem ipsum",
    val description: String? = "Lorem ipsum ".repeat(20),
    val category: NewsCategory? = NewsCategory.ENTERTAINMENT,
    val sourceName: String = "NYTimes",
    val sourceIconUrl: String? = "icon",
    val creator: String? = "Tucker Carlson",
    val imageUrl: String? = "url..",
    val pubDate: LocalDateTime = LocalDateTime.now(),
    val isFavorite: Boolean = true,
    val isRead: Boolean = false
)
