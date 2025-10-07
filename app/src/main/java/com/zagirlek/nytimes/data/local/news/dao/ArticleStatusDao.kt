package com.zagirlek.nytimes.data.local.news.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.zagirlek.nytimes.data.local.news.entity.ArticleStatusEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticleStatusDao {
    @Upsert
    suspend fun upsertArticle(article: ArticleStatusEntity)

    suspend fun updateArticleStatus(articleId: String, isRead: Boolean, isFavorite: Boolean) {
        upsertArticle(ArticleStatusEntity(articleId = articleId, isRead = isRead, isFavorite = isFavorite))
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