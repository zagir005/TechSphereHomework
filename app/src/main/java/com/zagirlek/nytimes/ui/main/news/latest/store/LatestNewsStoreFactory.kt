package com.zagirlek.nytimes.ui.main.news.latest.store

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.zagirlek.nytimes.R
import com.zagirlek.common.model.NewsCategory
import com.zagirlek.news.usecase.LatestNewsPagingUseCase
import com.zagirlek.news.usecase.ToggleArticleFavoriteStatusUseCase
import com.zagirlek.news.usecase.ToggleArticleReadStatusUseCase
import com.zagirlek.nytimes.ui.main.news.latest.model.toArticleFlow
import com.zagirlek.nytimes.ui.main.news.latest.store.LatestNewsStore.Intent
import com.zagirlek.nytimes.ui.main.news.latest.store.LatestNewsStore.Label
import com.zagirlek.nytimes.ui.main.news.latest.store.LatestNewsStore.State
import com.zagirlek.nytimes.ui.main.news.latest.store.LatestNewsStoreFactory.Action.LoadNews
import com.zagirlek.nytimes.ui.main.news.latest.store.LatestNewsStoreFactory.Msg.CategoryValue
import com.zagirlek.nytimes.ui.main.news.latest.store.LatestNewsStoreFactory.Msg.SearchFieldValue
import com.zagirlek.nytimes.ui.main.news.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LatestNewsStoreFactory(
    private val storeFactory: StoreFactory,
    private val latestNewsPagingUseCase: LatestNewsPagingUseCase,
    private val toggleFavoriteStatusUseCase: ToggleArticleFavoriteStatusUseCase,
    private val toggleReadStatusUseCase: ToggleArticleReadStatusUseCase,
) {
    fun create(): LatestNewsStore =
        object : LatestNewsStore, Store<Intent, State, Label> by storeFactory.create(
            name = "newsStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(LoadNews()),
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

    private inner class Executor: CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeAction(action: Action) =
            when(action) {
                is LoadNews -> {
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

        override fun executeIntent(intent: Intent){
            when(intent){
                is Intent.SearchFieldChange -> {
                    dispatch(
                        SearchFieldValue(intent.text)
                    )
                    executeAction(
                        action = LoadNews(
                            searchQuery = intent.text,
                            category = state().selectedCategory
                        )
                    )
                }
                is Intent.SelectCategory -> {
                    dispatch(
                        CategoryValue(intent.category)
                    )
                    executeAction(
                        action = LoadNews(
                            searchQuery = state().searchField,
                            category = intent.category
                        )
                    )
                }

                is Intent.ToggleArticleFavoriteStatus -> {
                    scope.launch {
                        toggleFavoriteStatusUseCase(intent.articleId)
                            .onFailure {
                                publish(Label.ShowError(
                                    R.string.article_not_saved_check_network_connection
                                ))
                            }
                    }
                }
                is Intent.ToggleArticleReadStatus -> {
                    scope.launch {
                        toggleReadStatusUseCase(intent.articleId)
                    }
                }
            }
        }
    }

    private object ReducerImpl: Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.LoadNews -> copy(newsFlow = msg.news)
                is CategoryValue -> copy(selectedCategory = msg.category)
                is SearchFieldValue -> copy(searchField = msg.value)
            }
    }
}