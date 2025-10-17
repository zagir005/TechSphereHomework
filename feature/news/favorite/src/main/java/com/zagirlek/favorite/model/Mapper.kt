package com.zagirlek.favorite.model

import com.zagirlek.favorite.store.FavoriteNewsStore
import com.zagirlek.news.model.ArticleLiteWithStatus
import com.zagirlek.ui.elements.toUiCategory

internal fun List<ArticleLiteWithStatus>.toFavoriteArticle(): List<FavoriteArticle> = map {
    it.toFavoriteArticle()
}

internal fun ArticleLiteWithStatus.toFavoriteArticle(): FavoriteArticle = FavoriteArticle(
    articleId = articleId,
    title = title,
    imageUrl = imageUrl,
    category = category.toUiCategory(),
    isFavorite = isFavorite
)

internal fun FavoriteNewsStore.State.toModel(): FavoriteNewsModel = FavoriteNewsModel(
    newsList = newsList.toFavoriteArticle(),
    selectedCategory = selectedCategory?.toUiCategory(),
    searchFieldValue = searchFieldValue
)