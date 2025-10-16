package com.zagirlek.nytimes.ui.main.news.favorite.model

import com.zagirlek.ui.elements.NewsCategoryUi

data class FavoriteNewsModel(
    val newsList: List<FavoriteArticle> = emptyList(),
    val selectedCategory: NewsCategoryUi? = null,
    val searchFieldValue: String? = null
)
