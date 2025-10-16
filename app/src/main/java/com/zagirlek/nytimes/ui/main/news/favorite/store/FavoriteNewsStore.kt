package com.zagirlek.nytimes.ui.main.news.favorite.store

import com.arkivanov.mvikotlin.core.store.Store
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.domain.model.ArticleLiteWithStatus
import com.zagirlek.nytimes.ui.main.news.favorite.store.FavoriteNewsStore.Intent
import com.zagirlek.nytimes.ui.main.news.favorite.store.FavoriteNewsStore.State

interface FavoriteNewsStore: Store<Intent, State, Nothing> {
    data class State (
        val newsList: List<ArticleLiteWithStatus> = emptyList(),
        val selectedCategory: NewsCategory? = null,
        val searchFieldValue: String? = null
    )

    sealed class Intent {
        data class SelectCategory(val category: NewsCategory?): Intent()
        data class SearchFieldChange(val text: String?): Intent()
        data class ToggleArticleFavoriteStatus(val articleId: String): Intent()
    }
}