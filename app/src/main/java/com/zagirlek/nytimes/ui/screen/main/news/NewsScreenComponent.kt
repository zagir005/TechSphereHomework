package com.zagirlek.nytimes.ui.screen.main.news

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.core.utils.getStore
import com.zagirlek.nytimes.domain.usecase.GetArticleStatusFlowUseCase
import com.zagirlek.nytimes.domain.usecase.GetPagingNewsUseCase
import com.zagirlek.nytimes.domain.usecase.UpdateArticleStatusUseCase
import com.zagirlek.nytimes.ui.screen.main.news.model.Article
import com.zagirlek.nytimes.ui.screen.main.news.model.NewsModel
import com.zagirlek.nytimes.ui.screen.main.news.model.toModel
import com.zagirlek.nytimes.ui.screen.main.news.store.NewsStore
import com.zagirlek.nytimes.ui.screen.main.news.store.NewsStoreFactory
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
    private val updateArticleStatusUseCase: UpdateArticleStatusUseCase,
    private val getArticleStatusFlowUseCase: GetArticleStatusFlowUseCase
): ComponentContext by componentContext, NewsScreen {
    private val componentScope = coroutineScope(
        context = SupervisorJob() + Dispatchers.Main.immediate
    ).also {
        doOnDestroy { it.cancel() }
    }

    private val storeInstance = instanceKeeper.getStore{
        NewsStoreFactory(
            storeFactory = storeFactory,
            updateArticleStatusUseCase = updateArticleStatusUseCase,
            getPagingNewsUseCase = getPagingNewsUseCase,
            getArticleStatusFlowUseCase = getArticleStatusFlowUseCase
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

    override fun toggleArticleReadStatus(article: Article) =
        storeInstance.accept(
            intent = NewsStore.Intent.ToggleArticleStatus(
                articleId = article.articleId,
                isFavorite = article.isFavorite,
                isRead = !article.isRead
            )
        )


    override fun toggleArticleFavoriteStatus(article: Article) =
        storeInstance.accept(
            intent = NewsStore.Intent.ToggleArticleStatus(
                articleId = article.articleId,
                isFavorite = !article.isFavorite,
                isRead = article.isRead
            )
        )


    override fun searchByTitle(query: String?) =
        storeInstance.accept(NewsStore.Intent.SearchFieldChange(query))

    override fun filterByCategory(category: NewsCategory?) =
        storeInstance.accept(NewsStore.Intent.SelectCategory(category))
}