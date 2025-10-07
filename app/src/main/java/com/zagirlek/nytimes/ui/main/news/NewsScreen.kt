package com.zagirlek.nytimes.ui.main.news

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.ui.main.articledetails.ArticleDetailsComponent
import com.zagirlek.nytimes.ui.main.news.model.NewsModel
import com.zagirlek.nytimes.ui.main.news.model.NewsSideEffect
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface NewsScreen {
    val model: StateFlow<NewsModel>
    val sideEffect: SharedFlow<NewsSideEffect>
    val dialog: Value<ChildSlot<*, ArticleDetailsComponent>>
    fun toggleArticleReadStatus(articleId: String)
    fun toggleArticleFavoriteStatus(articleId: String)
    fun searchByTitle(query: String?)
    fun filterByCategory(category: NewsCategory?)
    fun showArticleDetails(articleId: String)
    fun hideArticleDetails()
}