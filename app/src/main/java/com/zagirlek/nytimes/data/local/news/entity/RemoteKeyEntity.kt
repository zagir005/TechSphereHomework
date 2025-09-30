package com.zagirlek.nytimes.data.local.news.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.zagirlek.nytimes.domain.model.NewsFilter
@Entity(
    tableName = "remote_keys"
)
@TypeConverters(::class)
data class RemoteKeyEntity(
    @PrimaryKey
    val filters: NewsFilter,
    val pageKey: String,
    val nextPageKey: String?,
    val previousPageKey: String?
)