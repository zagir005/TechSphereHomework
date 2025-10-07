package com.zagirlek.nytimes.ui.main.news.store

import androidx.paging.PagingData
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.core.error.ExtractorApiError
import com.zagirlek.nytimes.core.error.toExtractorApiError
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.core.ui.model.Article
import com.zagirlek.nytimes.domain.usecase.news.ToggleArticleFavoriteStatusUseCase
import com.zagirlek.nytimes.domain.usecase.news.ToggleArticleReadStatusUseCase
import com.zagirlek.nytimes.ui.main.news.store.NewsStore.Intent
import com.zagirlek.nytimes.ui.main.news.store.NewsStore.Label
import com.zagirlek.nytimes.ui.main.news.store.NewsStore.Label.ShowError
import com.zagirlek.nytimes.ui.main.news.store.NewsStore.State
import com.zagirlek.nytimes.ui.main.news.store.NewsStoreFactory.Action.LoadNews
import com.zagirlek.nytimes.ui.main.news.store.NewsStoreFactory.Msg.CategoryValue
import com.zagirlek.nytimes.ui.main.news.store.NewsStoreFactory.Msg.SearchFieldValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NewsStoreFactory(
    private val storeFactory: StoreFactory,
    private val loadPagingNews: (category: NewsCategory?,
                                 titleQuery: String?) -> Flow<PagingData<Article>>,
    private val toggleFavoriteStatusUseCase: ToggleArticleFavoriteStatusUseCase,
    private val toggleReadStatusUseCase: ToggleArticleReadStatusUseCase,
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
                            news = loadPagingNews(action.category, action.searchQuery)
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
                    }
                }
                is Intent.ToggleArticleReadStatus -> {
                    scope.launch {
                        toggleReadStatusUseCase(intent.articleId)
                    }
                }

                is Intent.ShowError -> publish(
                    ShowError(
                        when(intent.cause.toExtractorApiError()){
                            ExtractorApiError.RateLimitReached -> R.string.usage_limit_reached
                            ExtractorApiError.ResourceNotFound -> R.string.article_not_found
                            ExtractorApiError.ServerError -> R.string.server_error
                            is ExtractorApiError.UnknownError -> R.string.unknown_error
                            ExtractorApiError.UsageLimitReached -> R.string.rate_limit_reached
                        }
                    )
                )
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