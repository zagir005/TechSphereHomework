package com.zagirlek.local.news.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.TypeConverters
import com.zagirlek.local.news.converters.ArticleConverter
import com.zagirlek.local.news.entity.RemoteKeyEntity

@Dao
@TypeConverters(ArticleConverter::class)
interface RemoteKeyDao {
    @Transaction
    @Query("SELECT * FROM remote_keys LIMIT 1")
    suspend fun lastRemoteKey(): RemoteKeyEntity?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKeyEntity)

    @Query("DELETE FROM remote_keys")
    suspend fun clearKey()
}