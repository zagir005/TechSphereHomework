package com.zagirlek.local.news.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class ArticleFullWithStatusEntity(
    @Embedded val article: ArticleFullEntity,
    @ColumnInfo(name = "isfavorite") val isFavorite: Boolean?,
    @ColumnInfo(name = "isread") val isRead: Boolean?
)