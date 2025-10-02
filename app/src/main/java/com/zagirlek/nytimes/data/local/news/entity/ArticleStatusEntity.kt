package com.zagirlek.nytimes.data.local.news.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_status_info")
data class ArticleStatusEntity(
    @PrimaryKey @ColumnInfo(name = "articleid") val articleId: String,
    @ColumnInfo(name = "isfavorite") val isFavorite: Boolean = false,
    @ColumnInfo(name = "isread") val isRead: Boolean = false
)
