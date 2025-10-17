package com.zagirlek.latest

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.zagirlek.articledetails.ArticleDetailsComponent
import com.zagirlek.common.model.NewsCategory
import com.zagirlek.latest.model.LatestNewsModel
import com.zagirlek.latest.model.NewsSideEffect
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface LatestNewsScreen {
    val model: StateFlow<LatestNewsModel>
    val sideEffect: SharedFlow<NewsSideEffect>
    val dialog: Value<ChildSlot<*, ArticleDetailsComponent>>
    fun toggleArticleReadStatus(articleId: String)
    fun toggleArticleFavoriteStatus(articleId: String)
    fun searchByTitle(query: String?)
    fun filterByCategory(category: NewsCategory?)
    fun showArticleDetails(articleId: String)
    fun hideArticleDetails()
}