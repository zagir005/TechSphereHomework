package com.zagirlek.local.news.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zagirlek.local.news.entity.ArticleStatusEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticleStatusDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIfAbsent(entity: ArticleStatusEntity)

    @Query("UPDATE article_status_info SET isfavorite = NOT isfavorite WHERE articleid = :articleId")
    suspend fun doToggleFavorite(articleId: String): Int

    @Query("UPDATE article_status_info SET isread = NOT isread WHERE articleid = :articleId")
    suspend fun doToggleRead(articleId: String): Int

    @Transaction
    suspend fun toggleFavoriteStatusAtomic(articleId: String) {
        insertIfAbsent(ArticleStatusEntity(articleId = articleId))
        doToggleFavorite(articleId)
    }

    @Transaction
    suspend fun toggleReadStatusAtomic(articleId: String) {
        insertIfAbsent(ArticleStatusEntity(articleId = articleId))
        doToggleRead(articleId)
    }

    @Query("SELECT * FROM article_status_info")
    fun getArticleStatusFlow(): Flow<List<ArticleStatusEntity>>

    @Query("SELECT * FROM article_status_info")
    fun getAllArticleStatusList(): List<ArticleStatusEntity>


    @Query("SELECT * FROM article_status_info WHERE isread = 1 OR isfavorite = 1")
    fun getAllNonDefaultArticleLocalStatus(): List<ArticleStatusEntity>

    @Query("SELECT * FROM article_status_info WHERE articleid = :articleId LIMIT 1")
    suspend fun getArticleStatusById(articleId: String): ArticleStatusEntity?
}