package com.zagirlek.nytimes.ui.main.news.favorite.model

import com.zagirlek.news.model.ArticleLiteWithStatus
import com.zagirlek.nytimes.ui.main.news.favorite.store.FavoriteNewsStore
import com.zagirlek.ui.elements.toUiCategory

fun List<ArticleLiteWithStatus>.toFavoriteArticle(): List<FavoriteArticle> = map {
    it.toFavoriteArticle()
}

fun ArticleLiteWithStatus.toFavoriteArticle(): FavoriteArticle = FavoriteArticle(
    articleId = articleId,
    title = title,
    imageUrl = imageUrl,
    category = category.toUiCategory(),
    isFavorite = isFavorite
)

fun FavoriteNewsStore.State.toModel(): FavoriteNewsModel = FavoriteNewsModel(
    newsList = newsList.toFavoriteArticle(),
    selectedCategory = selectedCategory?.toUiCategory(),
    searchFieldValue = searchFieldValue
)