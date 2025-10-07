package com.zagirlek.nytimes.data.local.news.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.zagirlek.nytimes.data.local.news.converters.ArticleConverter


@Entity(tableName = "remote_keys")
@TypeConverters(ArticleConverter::class)
data class RemoteKeyEntity(
    @PrimaryKey val nextPageKey: String
)