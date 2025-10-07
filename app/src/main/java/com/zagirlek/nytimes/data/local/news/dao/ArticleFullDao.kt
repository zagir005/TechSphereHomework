package com.zagirlek.nytimes.data.local.news.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.data.local.news.entity.ArticleFullEntity
import com.zagirlek.nytimes.data.local.news.entity.ArticleFullWithStatusEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleFullDao {
    @Insert
    suspend fun insert(article: ArticleFullEntity)

    @Query("SELECT * FROM article_full WHERE articleid = :articleId LIMIT 1")
    suspend fun getById(articleId: String): ArticleFullEntity?

    @Query("""
    SELECT f.*, s.isfavorite, s.isread
    FROM article_full AS f
    INNER JOIN article_status_info AS s
        ON f.articleid = s.articleid
    WHERE s.isfavorite = 1
      AND (:titleQuery IS NULL OR f.title LIKE '%' || :titleQuery || '%' COLLATE NOCASE)
      AND (:category IS NULL OR f.category = :category)
    ORDER BY f.pubdate DESC
    """)
    fun getFavoriteArticlesPaging(
        titleQuery: String?,
        category: NewsCategory?
    ): PagingSource<Int, ArticleFullWithStatusEntity>


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