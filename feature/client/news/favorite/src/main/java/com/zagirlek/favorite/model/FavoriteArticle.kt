package com.zagirlek.favorite.model

import com.zagirlek.ui.elements.newscategory.NewsCategoryUi

data class FavoriteArticle(
    val articleId: String,
    val title: String,
    val imageUrl: String?,
    val category: NewsCategoryUi,
    val isFavorite: Boolean
)
