package com.zagirlek.nytimes.data.local.news.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import com.zagirlek.nytimes.data.local.news.converters.ArticleConverter
import com.zagirlek.nytimes.data.local.news.entity.RemoteKeyEntity
import com.zagirlek.nytimes.domain.model.NewsFilter

@Dao
@TypeConverters(ArticleConverter::class)
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKeyEntity)

    @Query("SELECT * FROM remote_keys WHERE filters = :filters")
    suspend fun remoteKeysByFilters(filters: NewsFilter): RemoteKeyEntity?

    @Query("DELETE FROM remote_keys WHERE filters = :filters")
    suspend fun deleteByFilters(filters: NewsFilter)

    @Query("DELETE FROM remote_keys")
    suspend fun clearAll()
}