package com.zagirlek.nytimes.data.local.news.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.data.local.news.converters.ArticleConverter
import com.zagirlek.nytimes.data.local.news.entity.ArticleLiteEntity
import com.zagirlek.nytimes.data.local.news.entity.ArticleLiteWithStatusEntity

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
        WHERE (:titleQuery IS NULL OR l.title LIKE '%' || :titleQuery || '%')
        AND (:category IS NULL OR l.category = :category)
        ORDER BY l.pubdate DESC
    """)
    fun getArticlesWithStatusPaging(
        titleQuery: String?,
        category: NewsCategory?
    ): PagingSource<Int, ArticleLiteWithStatusEntity>

    @Query("SELECT * FROM article_lite WHERE articleid = :articleId LIMIT 1")
    suspend fun getById(articleId: String): ArticleLiteEntity?

    @Query("DELETE FROM article_lite")
    suspend fun deleteAll()
}