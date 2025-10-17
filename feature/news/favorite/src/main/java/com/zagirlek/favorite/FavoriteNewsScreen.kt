package com.zagirlek.favorite

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.zagirlek.articledetails.ArticleDetailsComponent
import com.zagirlek.common.model.NewsCategory
import com.zagirlek.favorite.model.FavoriteNewsModel
import kotlinx.coroutines.flow.StateFlow

interface FavoriteNewsScreen {
    val model: StateFlow<FavoriteNewsModel>
    val dialog: Value<ChildSlot<*, ArticleDetailsComponent>>
    fun toggleArticleFavoriteStatus(articleId: String)
    fun searchByTitle(query: String?)
    fun filterByCategory(category: NewsCategory?)
    fun showArticleDetails(articleId: String)
    fun hideArticleDetails()
}