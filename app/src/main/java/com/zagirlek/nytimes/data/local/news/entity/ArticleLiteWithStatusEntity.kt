package com.zagirlek.nytimes.data.local.news.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class ArticleLiteWithStatusEntity(
    @Embedded val article: ArticleLiteEntity,
    @ColumnInfo(name = "isfavorite") val isFavorite: Boolean?,
    @ColumnInfo(name = "isread") val isRead: Boolean?
)
