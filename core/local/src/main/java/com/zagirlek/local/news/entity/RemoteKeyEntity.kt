package com.zagirlek.local.news.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.zagirlek.local.news.converters.ArticleConverter


@Entity(tableName = "remote_keys")
@TypeConverters(ArticleConverter::class)
data class RemoteKeyEntity(
    @PrimaryKey val nextPageKey: String
)