package com.zagirlek.nytimes.ui.main.news.articledetails.cmp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.zagirlek.nytimes.core.utils.getStore
import com.zagirlek.nytimes.domain.usecase.news.GetArticleFullFlowUseCase
import com.zagirlek.nytimes.domain.usecase.news.ToggleArticleFavoriteStatusUseCase
import com.zagirlek.nytimes.domain.usecase.news.ToggleArticleReadStatusUseCase
import com.zagirlek.nytimes.ui.main.news.articledetails.ArticleDetailsComponent
import com.zagirlek.nytimes.ui.main.news.articledetails.store.ArticleDetailsStore
import com.zagirlek.nytimes.ui.main.news.articledetails.store.ArticleDetailsStoreFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow

class DefaultArticleDetailsComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val articleId: String,
    private val getArticleFullFlowUseCase: GetArticleFullFlowUseCase,
    private val toggleFavoriteStatusUseCase: ToggleArticleFavoriteStatusUseCase,
    private val toggleReadStatusUseCase: ToggleArticleReadStatusUseCase,
): ArticleDetailsComponent, ComponentContext by componentContext {
    val componentScope = coroutineScope(context = SupervisorJob() + Dispatchers.Main.immediate).also {
        doOnDestroy { it.cancel() }
    }
    val storeInstance: ArticleDetailsStore = instanceKeeper.getStore {
        ArticleDetailsStoreFactory(
            storeFactory = storeFactory,
            articleId = articleId,
            toggleFavoriteUseCase = toggleFavoriteStatusUseCase,
            toggleReadUseCase = toggleReadStatusUseCase,
            getArticleFullFlowUseCase = getArticleFullFlowUseCase
        ).create()
    }
    override val article: StateFlow<ArticleDetailsStore.State> = storeInstance
        .stateFlow(
            scope = componentScope,
            started = SharingStarted.WhileSubscribed(5000)
        )

    override fun toggleFavoriteStatus() =
        storeInstance.accept(ArticleDetailsStore.Intent.ToggleFavorite)


    override fun toggleReadStatus() =
        storeInstance.accept(ArticleDetailsStore.Intent.ToggleRead)

    override fun retry() =
        storeInstance.accept(ArticleDetailsStore.Intent.Retry)
}