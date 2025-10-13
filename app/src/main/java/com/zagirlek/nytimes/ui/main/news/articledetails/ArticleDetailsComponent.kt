package com.zagirlek.nytimes.ui.main.news.articledetails

import com.zagirlek.nytimes.ui.main.news.articledetails.store.ArticleDetailsStore
import kotlinx.coroutines.flow.StateFlow

interface ArticleDetailsComponent{
    val article: StateFlow<ArticleDetailsStore.State>
    fun toggleFavoriteStatus()
    fun toggleReadStatus()
    fun retry()
}