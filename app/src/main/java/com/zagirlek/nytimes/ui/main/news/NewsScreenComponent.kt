package com.zagirlek.nytimes.ui.main.news

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.zagirlek.nytimes.core.model.NewsCategory

import com.zagirlek.nytimes.core.utils.getStore
import com.zagirlek.nytimes.domain.usecase.news.GetPagingNewsUseCase
import com.zagirlek.nytimes.domain.usecase.news.ToggleArticleFavoriteStatusUseCase
import com.zagirlek.nytimes.domain.usecase.news.ToggleArticleReadStatusUseCase
import com.zagirlek.nytimes.ui.main.news.model.NewsModel
import com.zagirlek.nytimes.ui.main.news.model.toModel
import com.zagirlek.nytimes.ui.main.news.store.NewsStore
import com.zagirlek.nytimes.ui.main.news.store.NewsStoreFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class NewsScreenComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val getPagingNewsUseCase: GetPagingNewsUseCase,
    private val toggleFavoriteStatusUseCase: ToggleArticleFavoriteStatusUseCase,
    private val toggleReadStatusUseCase: ToggleArticleReadStatusUseCase
): ComponentContext by componentContext, NewsScreen {
    private val componentScope = coroutineScope(
        context = SupervisorJob() + Dispatchers.Main.immediate
    ).also {
        doOnDestroy { it.cancel() }
    }

    private val storeInstance = instanceKeeper.getStore {
        NewsStoreFactory(
            storeFactory = storeFactory,
            getPagingNewsUseCase = getPagingNewsUseCase,
            toggleReadStatusUseCase = toggleReadStatusUseCase,
            toggleFavoriteStatusUseCase = toggleFavoriteStatusUseCase
        ).create()
    }

    override val model: StateFlow<NewsModel> = storeInstance
        .stateFlow(lifecycle)
        .map {
            it.toModel()
        }
        .stateIn(
            scope = componentScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = NewsModel()
        )

    override fun toggleArticleReadStatus(articleId: String) =
        storeInstance.accept(
            intent = NewsStore.Intent.ToggleArticleReadStatus(
                articleId = articleId,
            )
        )


    override fun toggleArticleFavoriteStatus(articleId: String) =
        storeInstance.accept(
            intent = NewsStore.Intent.ToggleArticleFavoriteStatus(
                articleId = articleId,
            )
        )

    override fun searchByTitle(query: String?) =
        storeInstance.accept(NewsStore.Intent.SearchFieldChange(query))

    override fun filterByCategory(category: NewsCategory?) =
        storeInstance.accept(NewsStore.Intent.SelectCategory(category))
}