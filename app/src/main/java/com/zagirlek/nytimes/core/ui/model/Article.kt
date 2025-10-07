package com.zagirlek.nytimes.core.ui.model

import com.zagirlek.nytimes.core.model.NewsCategory

data class Article(
    val articleId: String,
    val title: String,
    val category: NewsCategory?,
    val imageUrl: String?,
    val creator: String,
    val readTime: String,
    val pubDate: String,
    val description: String?,
    val isRead: Boolean,
    val isFavorite: Boolean
){
    companion object{
        fun getExampleArticle() = Article(
            articleId = "",
            title = "Lorem ipsum",
            category = NewsCategory.OTHER,
            imageUrl = null,
            creator = "Zagir",
            readTime = "5 минут",
            pubDate = "2 дня назад",
            description = "Lorem ipsum ".repeat(20),
            isRead = true,
            isFavorite = false
        )
    }
}