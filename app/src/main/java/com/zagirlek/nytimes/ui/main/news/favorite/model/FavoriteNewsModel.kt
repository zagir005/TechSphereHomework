package com.zagirlek.nytimes.ui.main.news.favorite.model

import com.zagirlek.nytimes.core.model.NewsCategory

data class FavoriteNewsModel(
    val newsList: List<FavoriteArticle> = emptyList(),
    val selectedCategory: NewsCategory? = null,
    val searchFieldValue: String? = null
)
