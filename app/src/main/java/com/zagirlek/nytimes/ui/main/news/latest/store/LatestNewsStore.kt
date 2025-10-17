package com.zagirlek.nytimes.ui.main.news.latest.store

import androidx.paging.PagingData
import com.arkivanov.mvikotlin.core.store.Store
import com.zagirlek.common.model.NewsCategory
import com.zagirlek.nytimes.ui.main.news.latest.store.LatestNewsStore.Intent
import com.zagirlek.nytimes.ui.main.news.latest.store.LatestNewsStore.Label
import com.zagirlek.nytimes.ui.main.news.latest.store.LatestNewsStore.State
import com.zagirlek.nytimes.ui.main.news.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface LatestNewsStore: Store<Intent, State, Label> {
    data class State (
        val newsFlow: Flow<PagingData<Article>> = emptyFlow(),
        val selectedCategory: NewsCategory? = null,
        val searchField: String? = null
    )

    sealed class Intent {
        data class SelectCategory(val category: NewsCategory?): Intent()
        data class SearchFieldChange(val text: String?): Intent()
        data class ToggleArticleReadStatus(val articleId: String): Intent()
        data class ToggleArticleFavoriteStatus(val articleId: String): Intent()
    }

    sealed class Label {
        data class ShowError(val msgRes: Int): Label()
    }
}