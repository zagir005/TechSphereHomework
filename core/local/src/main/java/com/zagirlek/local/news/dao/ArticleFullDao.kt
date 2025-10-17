package com.zagirlek.local.news.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zagirlek.local.news.entity.ArticleFullEntity
import com.zagirlek.local.news.entity.ArticleFullWithStatusEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleFullDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(article: ArticleFullEntity)

    @Query("SELECT * FROM article_full WHERE articleid = :articleId LIMIT 1")
    suspend fun getById(articleId: String): ArticleFullEntity?

    @Query("""
    SELECT f.*, s.isfavorite, s.isread
    FROM article_full AS f
    LEFT JOIN article_status_info AS s
        ON f.articleid = s.articleid
    WHERE f.articleid = :articleId
    LIMIT 1
    """)
    fun getArticleFullWithStatusByIdFlow(
        articleId: String
    ): Flow<ArticleFullWithStatusEntity?>
}