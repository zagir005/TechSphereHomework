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
import kotlinx.coroutines.flow.Flow

@Dao
@TypeConverters(
    ArticleConverter::class
)
interface ArticleLiteDao {
    // Вставка
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticle(article: ArticleLiteEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticles(articles: List<ArticleLiteEntity>)


    // Получение всех статей с фильтрацией - PagingSource
    @Query("""
        SELECT * FROM articles 
        WHERE (:category IS NULL OR category = :category)
        AND (:titleQuery IS NULL OR title LIKE '%' || :titleQuery || '%')
        ORDER BY pubDate DESC
    """)
    fun getArticlesPagingSource(
        category: NewsCategory? = null,
        titleQuery: String? = null
    ): PagingSource<Int, ArticleLiteEntity>

    // Получение всех статей с фильтрацией - Flow
    @Query("""
        SELECT * FROM articles 
        WHERE (:category IS NULL OR category = :category)
        AND (:titleQuery = '' OR title LIKE '%' || :titleQuery || '%')
        ORDER BY pubDate DESC
    """)
    fun getArticlesFlow(
        category: NewsCategory? = null,
        titleQuery: String = ""
    ): Flow<List<ArticleLiteEntity>>

    @Query("""
        SELECT * FROM articles 
        WHERE (:category IS NULL OR category = :category)
        AND (:titleQuery = '' OR title LIKE '%' || :titleQuery || '%')
        ORDER BY pubDate DESC
    """)
    fun getArticles(
        category: NewsCategory? = null,
        titleQuery: String = ""
    ): List<ArticleLiteEntity>

    // Получение статьи по ID
    @Query("SELECT * FROM articles WHERE articleId = :articleId LIMIT 1")
    suspend fun getArticleById(articleId: String): ArticleLiteEntity?

    // Получение статей по конкретной категории
    @Query("""
    SELECT * FROM articles 
    WHERE category = :category
    ORDER BY pubDate DESC
""")
    fun getFavoriteArticlesByCategoryFlow(category: NewsCategory): Flow<List<ArticleLiteEntity>>


    suspend fun deleteAllExcept(ids: List<String>) {
        if (ids.isEmpty()) {
            deleteAll()
        } else {
            deleteAllExceptQuery(ids)
        }
    }

    @Query("DELETE FROM articles WHERE articleid NOT IN (:ids)")
    suspend fun deleteAllExceptQuery(ids: List<String>)

    @Query("DELETE FROM articles")
    suspend fun deleteAll()
}