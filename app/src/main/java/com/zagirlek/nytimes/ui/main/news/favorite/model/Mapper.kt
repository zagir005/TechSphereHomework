package com.zagirlek.nytimes.ui.main.news.favorite.model

import com.zagirlek.nytimes.domain.model.ArticleLiteWithStatus
import com.zagirlek.nytimes.ui.main.news.favorite.store.FavoriteNewsStore

fun List<ArticleLiteWithStatus>.toFavoriteArticle(): List<FavoriteArticle> = map {
    it.toFavoriteArticle()
}

fun ArticleLiteWithStatus.toFavoriteArticle(): FavoriteArticle = FavoriteArticle(
    articleId = articleId,
    title = title,
    imageUrl = imageUrl,
    category = category,
    isFavorite = isFavorite
)

fun FavoriteNewsStore.State.toModel(): FavoriteNewsModel = FavoriteNewsModel(
    newsList = newsList.toFavoriteArticle(),
    selectedCategory = selectedCategory,
    searchFieldValue = searchFieldValue
)