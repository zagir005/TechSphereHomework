package com.zagirlek.local.news.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.zagirlek.common.model.NewsCategory

@Entity(
    tableName = "article_lite",
    indices = [
        Index("category"),
        Index("title")
    ]
)
data class ArticleLiteEntity(
    @PrimaryKey @ColumnInfo(name = "articleid") val articleId: String,
    @ColumnInfo(name = "link") val link: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "sourcename") val sourceName: String,
    @ColumnInfo(name = "sourceiconurl") val sourceIconUrl: String?,
    @ColumnInfo(name = "category") val category: NewsCategory?,
    @ColumnInfo(name = "creator") val creator: String?,
    @ColumnInfo(name = "imageurl") val imageUrl: String?,
    @ColumnInfo(name = "pubdate") val pubDate: Long
)