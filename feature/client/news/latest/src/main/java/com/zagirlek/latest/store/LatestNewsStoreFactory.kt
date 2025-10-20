package com.zagirlek.latest.store

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.zagirlek.common.model.Article
import com.zagirlek.common.model.NewsCategory
import com.zagirlek.latest.model.toArticleFlow
import com.zagirlek.news.usecase.LatestNewsPagingUseCase
import com.zagirlek.news.usecase.ToggleArticleFavoriteStatusUseCase
import com.zagirlek.news.usecase.ToggleArticleReadStatusUseCase
import com.zagirlek.ui.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LatestNewsStoreFactory(
    private val storeFactory: StoreFactory,
    private val latestNewsPagingUseCase: LatestNewsPagingUseCase,
    private val toggleFavoriteStatusUseCase: ToggleArticleFavoriteStatusUseCase,
    private val toggleReadStatusUseCase: ToggleArticleReadStatusUseCase,
) {
    fun create(): LatestNewsStore =
        object : LatestNewsStore, Store<LatestNewsStore.Intent, LatestNewsStore.State, LatestNewsStore.Label> by storeFactory.create(
            name = "newsStore",
            initialState = LatestNewsStore.State(),
            bootstrapper = SimpleBootstrapper(Action.LoadNews()),
            executorFactory = ::Executor,
            reducer = ReducerImpl
        ) {}

    private sealed interface Msg {
        data class LoadNews(val news: Flow<PagingData<Article>>): Msg
        data class SearchFieldValue(val value: String?): Msg
        data class CategoryValue(val category: NewsCategory?): Msg
    }

    private sealed class Action{
        data class LoadNews(
            val searchQuery: String? = null,
            val category: NewsCategory? = null
        ): Action()
    }

    private inner class Executor: CoroutineExecutor<LatestNewsStore.Intent, Action, LatestNewsStore.State, Msg, LatestNewsStore.Label>() {
        override fun executeAction(action: Action) =
            when(action) {
                is Action.LoadNews -> {
                    dispatch(
                        Msg.LoadNews(
                            news = latestNewsPagingUseCase(
                                category = action.category,
                                titleQuery = action.searchQuery
                            )
                                .cachedIn(scope)
                                .toArticleFlow()
                        )
                    )
                }
            }

        override fun executeIntent(intent: LatestNewsStore.Intent){
            when(intent){
                is LatestNewsStore.Intent.SearchFieldChange -> {
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
                is LatestNewsStore.Intent.SelectCategory -> {
                    dispatch(
                        Msg.CategoryValue(intent.category)
                    )
                    executeAction(
                        action = Action.LoadNews(
                            searchQuery = state().searchField,
                            category = intent.category
                        )
                    )
                }

                is LatestNewsStore.Intent.ToggleArticleFavoriteStatus -> {
                    scope.launch {
                        toggleFavoriteStatusUseCase(intent.articleId)
                            .onFailure {
                                publish(
                                    LatestNewsStore.Label.ShowError(
                                    R.string.article_not_saved_check_network_connection
                                ))
                            }
                    }
                }
                is LatestNewsStore.Intent.ToggleArticleReadStatus -> {
                    scope.launch {
                        toggleReadStatusUseCase(intent.articleId)
                    }
                }
            }
        }
    }

    private object ReducerImpl: Reducer<LatestNewsStore.State, Msg> {
        override fun LatestNewsStore.State.reduce(msg: Msg): LatestNewsStore.State =
            when (msg) {
                is Msg.LoadNews -> copy(newsFlow = msg.news)
                is Msg.CategoryValue -> copy(selectedCategory = msg.category)
                is Msg.SearchFieldValue -> copy(searchField = msg.value)
            }
    }
}