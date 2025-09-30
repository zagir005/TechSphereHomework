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

    // Изменение значений isFavorite, isRead
    @Query("UPDATE articles SET isFavorite = :isFavorite WHERE articleId = :articleId")
    suspend fun updateFavorite(articleId: String, isFavorite: Boolean)

    @Query("UPDATE articles SET isRead = :isRead WHERE articleId = :articleId")
    suspend fun updateRead(articleId: String, isRead: Boolean)

    // Получение всех элементов с фильтрацией - PagingSource
    @Query("""
        SELECT * FROM articles 
        WHERE (:category IS NULL OR category = :category)
        AND (:titleQuery = '' OR title LIKE '%' || :titleQuery || '%')
        AND (:isFavorite IS NULL OR isFavorite = :isFavorite)
        AND (:isRead IS NULL OR isRead = :isRead)
        ORDER BY pubDate DESC
    """)
    fun getArticlesPagingSource(
        category: NewsCategory? = null,
        titleQuery: String = "",
        isFavorite: Boolean? = null,
        isRead: Boolean? = null
    ): PagingSource<Int, ArticleLiteEntity>

    // Получение всех элементов с фильтрацией - Flow
    @Query("""
        SELECT * FROM articles 
        WHERE (:category IS NULL OR category = :category)
        AND (:titleQuery = '' OR title LIKE '%' || :titleQuery || '%')
        AND (:isFavorite IS NULL OR isFavorite = :isFavorite)
        AND (:isRead IS NULL OR isRead = :isRead)
        ORDER BY pubDate DESC
    """)
    fun getArticlesFlow(
        category: NewsCategory? = null,
        titleQuery: String = "",
        isFavorite: Boolean? = null,
        isRead: Boolean? = null
    ): Flow<List<ArticleLiteEntity>>

    // Получение статьи по ID
    @Query("SELECT * FROM articles WHERE articleId = :articleId LIMIT 1")
    suspend fun getArticleById(articleId: String): ArticleLiteEntity?

    // Получение категорий из избранных статей
    @Query("SELECT DISTINCT category FROM articles WHERE isFavorite = 1 ORDER BY category ASC")
    fun getFavoriteCategoriesFlow(): Flow<List<NewsCategory>>

    // Получение избранных статей по конкретной категории
    @Query("""
    SELECT * FROM articles 
    WHERE isFavorite = 1 AND category = :category 
    ORDER BY pubDate DESC
""")
    fun getFavoriteArticlesByCategoryFlow(category: NewsCategory): Flow<List<ArticleLiteEntity>>

}