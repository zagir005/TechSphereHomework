package com.zagirlek.articledetails.store


import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.zagirlek.common.error.AppError
import com.zagirlek.common.error.ExtractorApiError
import com.zagirlek.common.error.NewsApiError
import com.zagirlek.news.model.ArticleFullWithStatus
import com.zagirlek.news.usecase.GetArticleFullFlowUseCase
import com.zagirlek.news.usecase.ToggleArticleFavoriteStatusUseCase
import com.zagirlek.news.usecase.ToggleArticleReadStatusUseCase
import com.zagirlek.ui.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class ArticleDetailsStoreFactory(
    private val storeFactory: StoreFactory,
    private val articleId: String,
    private val toggleFavoriteUseCase: ToggleArticleFavoriteStatusUseCase,
    private val toggleReadUseCase: ToggleArticleReadStatusUseCase,
    private val getArticleFullFlowUseCase: GetArticleFullFlowUseCase
) {
    fun create(): ArticleDetailsStore = object : ArticleDetailsStore, Store<ArticleDetailsStore.Intent, ArticleDetailsStore.State, Nothing> by storeFactory.create(
        name = "article_details",
        initialState = ArticleDetailsStore.State(),
        bootstrapper = SimpleBootstrapper(Action.LoadArticle(articleId)),
        executorFactory = ::ExecutorImpl,
        reducer = ReducerImpl
    ) {}

    sealed class Msg{
        data class Article(val article: ArticleFullWithStatus): Msg()
        data class Error(val errorMsgRes: Int): Msg()
        object Loading: Msg()
    }

    sealed class Action{
        data class LoadArticle(val articleId: String): Action()
    }

    private inner class ExecutorImpl: CoroutineExecutor<ArticleDetailsStore.Intent, Action, ArticleDetailsStore.State, Msg, Nothing>() {
        override fun executeIntent(intent: ArticleDetailsStore.Intent){
            when(intent){
                ArticleDetailsStore.Intent.ToggleFavorite -> toggleFavoriteStatus()
                ArticleDetailsStore.Intent.ToggleRead -> toggleReadStatus()
                ArticleDetailsStore.Intent.Retry -> loadArticle(articleId)
            }
        }

        override fun executeAction(action: Action) {
            when(action){
                is Action.LoadArticle -> loadArticle(articleId)
            }
        }
        private fun toggleReadStatus() = scope.launch {
            toggleReadUseCase(articleId)
        }
        private fun toggleFavoriteStatus() = scope.launch {
            toggleFavoriteUseCase(articleId)
        }
        private fun loadArticle(articleId: String) = scope.launch {
            withContext(Dispatchers.Main) { dispatch(Msg.Loading) }
            getArticleFullFlowUseCase(articleId)
                .collect { result ->
                    result
                        .onSuccess {
                            withContext(Dispatchers.Main) {
                                dispatch(Msg.Article(it))
                            }
                        }
                        .onFailure { error ->
                            withContext(Dispatchers.Main) {
                                dispatch(
                                    Msg.Error(errorMsgRes = getErrorMsgByError(error))
                                )
                            }
                        }
                }
        }

        private fun getErrorMsgByError(error: Throwable) = when (error) {
            is NewsApiError -> {
                when (error) {
                    NewsApiError.TooManyRequests -> R.string.too_many_requests_try_later
                    else -> R.string.unknown_error
                }
            }

            is ExtractorApiError -> {
                when (error) {
                    ExtractorApiError.RateLimitReached -> R.string.too_many_requests_try_later
                    ExtractorApiError.UsageLimitReached -> R.string.too_many_requests_try_later
                    else -> R.string.unknown_error
                }
            }
            is AppError -> {
                when(error){
                    AppError.NoNetworkConnection -> R.string.no_network_connection_cant_load
                }
            }
            else -> R.string.unknown_error
        }
    }

    private object ReducerImpl: Reducer<ArticleDetailsStore.State, Msg>{
        override fun ArticleDetailsStore.State.reduce(
            msg: Msg
        ): ArticleDetailsStore.State = when(msg){
            is Msg.Article -> copy(
                article = msg.article,
                loading = false,
                errorMsgRes = null
            )
            is Msg.Error -> copy(
                article = null,
                loading = false,
                errorMsgRes = msg.errorMsgRes
            )

            Msg.Loading -> copy(
                article = null,
                loading = true,
                errorMsgRes = null
            )
        }
    }
}