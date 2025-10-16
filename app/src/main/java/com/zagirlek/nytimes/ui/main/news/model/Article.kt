package com.zagirlek.nytimes.ui.main.news.model

import com.zagirlek.common.model.NewsCategory

data class Article(
    val articleId: String = "",
    val title: String = "",
    val category: NewsCategory? = null,
    val imageUrl: String? = null,
    val creator: String = "",
    val readTime: String = "",
    val pubDate: String = "",
    val description: String? = null,
    val isRead: Boolean = false,
    val isFavorite: Boolean = false
){
    companion object{
        fun getExampleArticle(title: String = "Lorem ipsum") = Article(
            articleId = "",
            title = title,
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