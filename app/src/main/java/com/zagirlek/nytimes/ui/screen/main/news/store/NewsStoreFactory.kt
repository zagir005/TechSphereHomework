package com.zagirlek.nytimes.ui.screen.main.news.store

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.core.model.NewsFilter
import com.zagirlek.nytimes.domain.model.ArticleStatus
import com.zagirlek.nytimes.domain.usecase.GetArticleStatusFlowUseCase
import com.zagirlek.nytimes.domain.usecase.GetPagingNewsUseCase
import com.zagirlek.nytimes.domain.usecase.UpdateArticleStatusUseCase
import com.zagirlek.nytimes.ui.screen.main.news.model.Article
import com.zagirlek.nytimes.ui.screen.main.news.store.NewsStore.Intent
import com.zagirlek.nytimes.ui.screen.main.news.store.NewsStore.Label
import com.zagirlek.nytimes.ui.screen.main.news.store.NewsStore.State
import com.zagirlek.nytimes.ui.screen.main.news.store.NewsStoreFactory.Action.LoadNews
import com.zagirlek.nytimes.ui.screen.main.news.store.NewsStoreFactory.Msg.CategoryValue
import com.zagirlek.nytimes.ui.screen.main.news.store.NewsStoreFactory.Msg.SearchFieldValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class NewsStoreFactory(
    private val storeFactory: StoreFactory,
    private val updateArticleStatusUseCase: UpdateArticleStatusUseCase,
    private val getArticleStatusFlowUseCase: GetArticleStatusFlowUseCase,
    private val getPagingNewsUseCase: GetPagingNewsUseCase
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
                        LoadNews(
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
                        LoadNews(
                            searchQuery = state().searchField,
                            category = intent.category
                        )
                    )
                }
                is Intent.ToggleArticleStatus -> {
                    scope.launch {
                        updateArticleStatusUseCase(
                            articleId = intent.articleId,
                            isFavorite = intent.isFavorite,
                            isRead = intent.isRead
                        )
                    }
                }
            }
        }

        private fun loadNews(searchQuery: String? = null, category: NewsCategory? = null): Flow<PagingData<Article>>{
            val articleStatusFlow = getArticleStatusFlowUseCase()
            val pagingNewsFlow = getPagingNewsUseCase(NewsFilter(titleQuery = searchQuery, category = category))
                .cachedIn(scope)

            Log.d("MYTAGPAGING", "LoadNews fun invoke")

            return combine(pagingNewsFlow, articleStatusFlow){ pagingData, statusList ->
                pagingData.map { article ->
                    val status = statusList.find {  it.articleId == article.articleId } ?: ArticleStatus(
                        articleId = article.articleId,
                        isRead = false,
                        isFavorite = false
                    )
                    Article(
                        articleId = article.articleId,
                        isRead = status.isRead,
                        isFavorite = status.isFavorite,
                        link = article.link,
                        description = article.description,
                        title = article.title,
                        category = article.category,
                        sourceName = article.sourceName,
                        sourceIconUrl = article.sourceIconUrl,
                        pubDate = article.pubDate,
                        imageUrl = article.imageUrl,
                        creator = article.creator
                    )
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