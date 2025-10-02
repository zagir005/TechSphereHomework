package com.zagirlek.nytimes.data.local.news.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.TypeConverters
import com.zagirlek.nytimes.core.model.NewsFilter
import com.zagirlek.nytimes.data.local.news.converters.ArticleConverter
import com.zagirlek.nytimes.data.local.news.entity.RemoteKeyEntity

@Dao
@TypeConverters(ArticleConverter::class)
interface RemoteKeyDao {
    @Transaction
    @Query("SELECT * FROM remote_keys WHERE filters = :filters")
    suspend fun remoteKeysByFilters(filters: NewsFilter): RemoteKeyEntity?


    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKeyEntity)
}