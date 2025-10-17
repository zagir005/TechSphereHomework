package com.zagirlek.nytimes.ui.main.news.articledetails.store

import com.arkivanov.mvikotlin.core.store.Store
import com.zagirlek.news.model.ArticleFullWithStatus
import com.zagirlek.nytimes.ui.main.news.articledetails.store.ArticleDetailsStore.Intent
import com.zagirlek.nytimes.ui.main.news.articledetails.store.ArticleDetailsStore.State

interface ArticleDetailsStore: Store<Intent, State, Nothing> {
    sealed class Intent{
        data object ToggleFavorite: Intent()
        data object ToggleRead: Intent()
        data object Retry: Intent()
    }

    data class State(
        val article: ArticleFullWithStatus? = null,
        val errorMsgRes: Int? = null,
        val loading: Boolean = true
    )
}