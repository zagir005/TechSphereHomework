package com.zagirlek.nytimes.ui.screen.main.news

import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.ui.screen.main.news.model.Article
import com.zagirlek.nytimes.ui.screen.main.news.model.NewsModel
import kotlinx.coroutines.flow.StateFlow

interface NewsScreen {
    val model: StateFlow<NewsModel>
    fun toggleArticleReadStatus(article: Article)
    fun toggleArticleFavoriteStatus(article: Article)
    fun searchByTitle(query: String?)
    fun filterByCategory(category: NewsCategory?)
}