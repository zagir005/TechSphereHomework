package com.zagirlek.nytimes.ui.main.news.store

import androidx.paging.PagingData
import com.arkivanov.mvikotlin.core.store.Store
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.core.ui.model.Article
import com.zagirlek.nytimes.ui.main.news.store.NewsStore.Intent
import com.zagirlek.nytimes.ui.main.news.store.NewsStore.Label
import com.zagirlek.nytimes.ui.main.news.store.NewsStore.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


interface NewsStore: Store<Intent, State, Label> {
    data class State (
        val newsFlow: Flow<PagingData<Article>> = emptyFlow(),
        val selectedCategory: NewsCategory? = null,
        val selectedArticleId: String? = null,
        val searchField: String? = null
    )

    sealed class Intent {
        data class SelectCategory(val category: NewsCategory?): Intent()
        data class SearchFieldChange(val text: String?): Intent()
        data class ArticleDetails(val articleId: String): Intent()
        data class ToggleArticleReadStatus(val articleId: String): Intent()
        data class ToggleArticleFavoriteStatus(val articleId: String): Intent()
    }

    sealed class Label {
        data class ShowArticleDetails(val articleId: String): Label()
    }
}