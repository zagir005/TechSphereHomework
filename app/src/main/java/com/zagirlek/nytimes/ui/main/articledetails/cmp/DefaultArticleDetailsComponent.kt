package com.zagirlek.nytimes.ui.main.articledetails.cmp

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.zagirlek.nytimes.domain.model.ArticleFullWithStatus
import com.zagirlek.nytimes.domain.usecase.news.GetArticleFullByIdFlowUseCase
import com.zagirlek.nytimes.domain.usecase.news.ToggleArticleFavoriteStatusUseCase
import com.zagirlek.nytimes.domain.usecase.news.ToggleArticleReadStatusUseCase
import com.zagirlek.nytimes.ui.main.articledetails.ArticleDetailsComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DefaultArticleDetailsComponent(
    componentContext: ComponentContext,
    private val articleId: String,
    private val onErrorClose: (Throwable) -> Unit,
    private val getArticleFullByIdFlowUseCase: GetArticleFullByIdFlowUseCase,
    private val toggleFavoriteStatusUseCase: ToggleArticleFavoriteStatusUseCase,
    private val toggleReadStatusUseCase: ToggleArticleReadStatusUseCase,
): ArticleDetailsComponent, ComponentContext by componentContext {

    val componentScope = coroutineScope(context = SupervisorJob() + Dispatchers.Main.immediate).also {
        doOnDestroy { it.cancel() }
    }

    init {
        componentScope.launch {
            getArticleFullByIdFlowUseCase(articleId).collect { result ->
                result
                    .onSuccess { article ->
                        Log.d("MYTAGARTICLE", article.toString())
                        _article.update { article }
                    }
                    .onFailure {
                        Log.d("MYTAGARTICLE", it.toString())
                        onErrorClose(it)
                    }
            }
        }
    }

    private val _article: MutableStateFlow<ArticleFullWithStatus?> = MutableStateFlow(null)
    override val article: StateFlow<ArticleFullWithStatus?> = _article.asStateFlow()

    override fun toggleFavoriteStatus() {
        componentScope.launch {
            toggleFavoriteStatusUseCase(articleId)
        }
    }

    override fun toggleReadStatus(){
        componentScope.launch {
            toggleReadStatusUseCase(articleId)
        }
    }
}