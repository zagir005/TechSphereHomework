package com.zagirlek.nytimes.ui.main.news.favorite.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.zagirlek.common.model.NewsCategory
import com.zagirlek.news.model.ArticleLiteWithStatus
import com.zagirlek.news.usecase.FavoriteNewsFlowUseCase
import com.zagirlek.news.usecase.ToggleArticleFavoriteStatusUseCase
import com.zagirlek.nytimes.ui.main.news.favorite.store.FavoriteNewsStore.Intent
import com.zagirlek.nytimes.ui.main.news.favorite.store.FavoriteNewsStore.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteNewsStoreFactory(
    private val storeFactory: StoreFactory,
    private val toggleFavorite: ToggleArticleFavoriteStatusUseCase,
    private val favoriteNewsFlow: FavoriteNewsFlowUseCase
) {

    fun create(): FavoriteNewsStore =
        object : FavoriteNewsStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "newsStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Action.LoadNews()),
            executorFactory = ::Executor,
            reducer = ReducerImpl
        ) {}

    private sealed interface Msg {
        data class LoadNews(val news: List<ArticleLiteWithStatus>): Msg
        data class SearchFieldValue(val value: String?): Msg
        data class CategoryValue(val category: NewsCategory?): Msg
    }

    private sealed class Action{
        data class LoadNews(
            val searchQuery: String? = null,
            val category: NewsCategory? = null
        ): Action()
    }

    private inner class Executor: CoroutineExecutor<Intent, Action, State, Msg, Nothing>() {

        override fun executeIntent(intent: Intent){
            when(intent){
                is Intent.SearchFieldChange -> {
                    dispatch(
                        Msg.SearchFieldValue(intent.text)
                    )
                    executeAction(
                        action = Action.LoadNews(
                            searchQuery = intent.text,
                            category = state().selectedCategory
                        )
                    )
                }
                is Intent.SelectCategory -> {
                    dispatch(
                        Msg.CategoryValue(intent.category)
                    )
                    executeAction(
                        action = Action.LoadNews(
                            searchQuery = state().searchFieldValue,
                            category = intent.category
                        )
                    )
                }

                is Intent.ToggleArticleFavoriteStatus -> {
                    scope.launch {
                        toggleFavorite(intent.articleId)
                    }
                }
            }
        }

        override fun executeAction(action: Action) {
            when (action) {
                is Action.LoadNews -> {
                    scope.launch {
                        favoriteNewsFlow(
                            category = action.category,
                            titleQuery = action.searchQuery
                        ).collect { list ->
                            withContext(Dispatchers.Main) {
                                dispatch(
                                    Msg.LoadNews(
                                        news = list
                                    )
                                )
                            }
                        }
                    }

                }
            }
        }
    }

    private object ReducerImpl: Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.LoadNews -> copy(newsList = msg.news)
                is Msg.CategoryValue -> copy(selectedCategory = msg.category)
                is Msg.SearchFieldValue -> copy(searchFieldValue = msg.value)
            }
    }
}