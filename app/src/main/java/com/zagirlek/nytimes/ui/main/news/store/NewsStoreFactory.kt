package com.zagirlek.nytimes.ui.main.news.store

import androidx.paging.PagingData
import androidx.paging.map
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.core.ui.model.Article
import com.zagirlek.nytimes.domain.usecase.news.GetPagingNewsUseCase
import com.zagirlek.nytimes.domain.usecase.news.ToggleArticleFavoriteStatusUseCase
import com.zagirlek.nytimes.domain.usecase.news.ToggleArticleReadStatusUseCase
import com.zagirlek.nytimes.ui.main.news.model.toArticleItem
import com.zagirlek.nytimes.ui.main.news.store.NewsStore.Intent
import com.zagirlek.nytimes.ui.main.news.store.NewsStore.Label
import com.zagirlek.nytimes.ui.main.news.store.NewsStore.State
import com.zagirlek.nytimes.ui.main.news.store.NewsStoreFactory.Action.LoadNews
import com.zagirlek.nytimes.ui.main.news.store.NewsStoreFactory.Msg.CategoryValue
import com.zagirlek.nytimes.ui.main.news.store.NewsStoreFactory.Msg.SearchFieldValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class NewsStoreFactory(
    private val storeFactory: StoreFactory,
    private val getPagingNewsUseCase: GetPagingNewsUseCase,
    private val toggleFavoriteStatusUseCase: ToggleArticleFavoriteStatusUseCase,
    private val toggleReadStatusUseCase: ToggleArticleReadStatusUseCase
) {

    fun create(): NewsStore =
        object : NewsStore, Store<Intent, State, Label> by storeFactory.create(
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
        data class LoadNews(val searchQuery: String? = null, val category: NewsCategory? = null): Action()
    }

    private inner class Executor: CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeAction(action: Action) =
            when(action) {
                is LoadNews -> {
                    dispatch(
                        Msg.LoadNews(
                            news = loadNews(searchQuery = action.searchQuery, category = action.category)
                        )
                    )
                }
            }

        override fun executeIntent(intent: Intent){
            when(intent){
                is Intent.ArticleDetails -> {}
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
                    }
                }
                is Intent.ToggleArticleReadStatus -> {
                    scope.launch {
                        toggleReadStatusUseCase(intent.articleId)
                    }
                }
            }
        }

        private fun loadNews(searchQuery: String? = null, category: NewsCategory? = null): Flow<PagingData<Article>>{
            return getPagingNewsUseCase(titleQuery = searchQuery, category = category)
                .map { pagingData ->
                    pagingData.map { article ->
                        article.toArticleItem()
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