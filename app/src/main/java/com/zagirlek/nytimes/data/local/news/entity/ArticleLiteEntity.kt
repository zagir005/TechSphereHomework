package com.zagirlek.nytimes.data.local.news.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zagirlek.nytimes.domain.model.NewsCategory

@Entity(
    tableName = "articles"
)
data class ArticleLiteEntity(
    @PrimaryKey val articleId: String,
    val link: String,
    val title: String,
    val description: String,
    val sourceName: String,
    val sourceIconUrl: String,
    val category: NewsCategory,
    val creator: String,
    val imageUrl: String,
    val pubDate: Long,
    val isFavorite: Boolean = false,
    val isRead: Boolean = false
)
