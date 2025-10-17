package com.zagirlek.local.news.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import com.zagirlek.common.model.NewsCategory
import com.zagirlek.local.news.converters.ArticleConverter
import com.zagirlek.local.news.entity.ArticleLiteEntity
import com.zagirlek.local.news.entity.ArticleLiteWithStatusEntity
import kotlinx.coroutines.flow.Flow

@Dao
@TypeConverters(
    ArticleConverter::class
)
interface ArticleLiteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticle(article: ArticleLiteEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticles(articles: List<ArticleLiteEntity>)

    @Query("""
        SELECT l.*, s.isfavorite, s.isread
    FROM article_lite AS l
    LEFT JOIN article_status_info AS s
        ON l.articleid = s.articleid
    WHERE (:titleQuery IS NULL OR l.title LIKE '%' || :titleQuery || '%' COLLATE NOCASE)
      AND (:category IS NULL OR l.category = :category)
    ORDER BY l.pubdate DESC
    """)
    fun getArticlesWithStatusPaging(
        titleQuery: String?,
        category: NewsCategory?
    ): PagingSource<Int, ArticleLiteWithStatusEntity>

    @Query("""
    SELECT f.*, s.isfavorite, s.isread
    FROM article_lite AS f
    INNER JOIN article_status_info AS s
        ON f.articleid = s.articleid
    WHERE s.isfavorite = 1
      AND (:titleQuery IS NULL OR f.title LIKE '%' || :titleQuery || '%' COLLATE NOCASE)
      AND (:category IS NULL OR f.category = :category)
    ORDER BY f.pubdate DESC
    """)
    fun getFavoriteArticlesFlow(
        titleQuery: String?,
        category: NewsCategory?
    ): Flow<List<ArticleLiteWithStatusEntity>>

    @Query("SELECT * FROM article_lite WHERE articleid = :articleId LIMIT 1")
    suspend fun getById(articleId: String): ArticleLiteEntity?

    @Query("""
    DELETE FROM article_lite
        WHERE articleid NOT IN (
            SELECT articleid FROM article_status_info
            WHERE isFavorite = 1 OR isRead = 1
        )
    """)
    suspend fun deleteAllExceptWithStatus()
}