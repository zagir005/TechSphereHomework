package com.zagirlek.nytimes.ui.main.news

import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.ui.main.news.model.NewsModel
import kotlinx.coroutines.flow.StateFlow

interface NewsScreen {
    val model: StateFlow<NewsModel>
    fun toggleArticleReadStatus(articleId: String)
    fun toggleArticleFavoriteStatus(articleId: String)
    fun searchByTitle(query: String?)
    fun filterByCategory(category: NewsCategory?)
}