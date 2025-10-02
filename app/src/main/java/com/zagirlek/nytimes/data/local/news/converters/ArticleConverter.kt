package com.zagirlek.nytimes.data.local.news.converters

import androidx.room.TypeConverter
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.core.model.NewsFilter

class ArticleConverter{
    @TypeConverter
    fun toCategory(value: String): NewsCategory = enumValueOf(name = value)

    @TypeConverter
    fun fromCategory(value: NewsCategory): String = value.name

    @TypeConverter
    fun fromNewsFilter(value: NewsFilter?): String {
        return value?.let {
            val category = it.category?.name ?: ""
            val titleQuery = it.titleQuery ?: ""
            "$category/$titleQuery"
        } ?: ""
    }

    @TypeConverter
    fun toNewsFilter(value: String?): NewsFilter? {
        if (value.isNullOrBlank()) return null
        val parts = value.split("/")
        val rawCategory = parts.getOrNull(0)
        val parsedCategory = if (!rawCategory.isNullOrBlank()) NewsCategory.valueOf(rawCategory) else null
        val rawQuery = parts.getOrNull(1)
        val parsedQuery = rawQuery?.takeIf { it.isNotBlank() }
        return NewsFilter(
            category = parsedCategory,
            titleQuery = parsedQuery
        )
    }
}