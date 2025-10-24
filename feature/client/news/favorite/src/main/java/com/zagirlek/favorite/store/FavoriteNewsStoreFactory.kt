package com.zagirlek.favorite.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.zagirlek.common.model.NewsCategory
import com.zagirlek.news.model.ArticleLiteWithStatus
import com.zagirlek.news.usecase.FavoriteNewsFlowUseCase
import com.zagirlek.news.usecase.ToggleArticleFavoriteStatusUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class FavoriteNewsStoreFactory(
    private val storeFactory: StoreFactory,
    private val toggleFavorite: ToggleArticleFavoriteStatusUseCase,
    private val favoriteNewsFlow: FavoriteNewsFlowUseCase
) {

    fun create(): FavoriteNewsStore =
        object : FavoriteNewsStore, Store<FavoriteNewsStore.Intent, FavoriteNewsStore.State, Nothing> by storeFactory.create(
            name = "newsStore",
            initialState = FavoriteNewsStore.State(),
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

    private inner class Executor: CoroutineExecutor<FavoriteNewsStore.Intent, Action, FavoriteNewsStore.State, Msg, Nothing>() {

        override fun executeIntent(intent: FavoriteNewsStore.Intent){
            when(intent){
                is FavoriteNewsStore.Intent.SearchFieldChange -> {
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
                is FavoriteNewsStore.Intent.SelectCategory -> {
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

                is FavoriteNewsStore.Intent.ToggleArticleFavoriteStatus -> {
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

    private object ReducerImpl: Reducer<FavoriteNewsStore.State, Msg> {
        override fun FavoriteNewsStore.State.reduce(msg: Msg): FavoriteNewsStore.State =
            when (msg) {
                is Msg.LoadNews -> copy(newsList = msg.news)
                is Msg.CategoryValue -> copy(selectedCategory = msg.category)
                is Msg.SearchFieldValue -> copy(searchFieldValue = msg.value)
            }
    }
}