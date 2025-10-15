package com.zagirlek.nytimes.ui.main.news.favorite.model

import com.zagirlek.nytimes.core.model.NewsCategory

data class FavoriteArticle(
    val articleId: String,
    val title: String,
    val imageUrl: String?,
    val category: NewsCategory,
    val isFavorite: Boolean
)
