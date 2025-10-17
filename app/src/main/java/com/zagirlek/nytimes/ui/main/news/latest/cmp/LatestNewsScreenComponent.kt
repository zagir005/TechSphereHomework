package com.zagirlek.nytimes.ui.main.news.latest.cmp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.zagirlek.common.model.NewsCategory
import com.zagirlek.common.utils.getStore
import com.zagirlek.news.usecase.LatestNewsPagingUseCase
import com.zagirlek.news.usecase.ToggleArticleFavoriteStatusUseCase
import com.zagirlek.news.usecase.ToggleArticleReadStatusUseCase
import com.zagirlek.nytimes.ui.main.news.articledetails.ArticleDetailsComponent
import com.zagirlek.nytimes.ui.main.news.articledetails.cmp.ArticleDetailsComponentFactory
import com.zagirlek.nytimes.ui.main.news.latest.LatestNewsScreen
import com.zagirlek.nytimes.ui.main.news.latest.model.LatestNewsModel
import com.zagirlek.nytimes.ui.main.news.latest.model.NewsSideEffect
import com.zagirlek.nytimes.ui.main.news.latest.model.toModel
import com.zagirlek.nytimes.ui.main.news.latest.store.LatestNewsStore
import com.zagirlek.nytimes.ui.main.news.latest.store.LatestNewsStoreFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn

class LatestNewsScreenComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val articleDetailsComponentFactory: ArticleDetailsComponentFactory,
    private val latestNewsPagingUseCase: LatestNewsPagingUseCase,
    private val toggleFavoriteStatusUseCase: ToggleArticleFavoriteStatusUseCase,
    private val toggleReadStatusUseCase: ToggleArticleReadStatusUseCase,
): ComponentContext by componentContext, LatestNewsScreen {
    private val componentScope = coroutineScope(
        context = SupervisorJob() + Dispatchers.Main.immediate
    ).also {
        doOnDestroy { it.cancel() }
    }

    private val storeInstance = instanceKeeper.getStore {
        LatestNewsStoreFactory(
            storeFactory = storeFactory,
            latestNewsPagingUseCase = latestNewsPagingUseCase,
            toggleReadStatusUseCase = toggleReadStatusUseCase,
            toggleFavoriteStatusUseCase = toggleFavoriteStatusUseCase
        ).create()
    }

    override val sideEffect: SharedFlow<NewsSideEffect> = storeInstance
        .labels
        .map {
            when(it){
                is LatestNewsStore.Label.ShowError -> NewsSideEffect.ShowSnackbar(it.msgRes)
            }
        }
        .shareIn(
            scope = componentScope,
            started = SharingStarted.WhileSubscribed(),
            replay = 0
        )

    override val model: StateFlow<LatestNewsModel> = storeInstance
        .stateFlow(lifecycle)
        .map {
            it.toModel()
        }
        .stateIn(
            scope = componentScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = LatestNewsModel()
        )

    private val dialogNavigation = SlotNavigation<ArticleDetailsComponent.ArticleDetailsConfig>()
    override val dialog: Value<ChildSlot<*, ArticleDetailsComponent>> = childSlot(
            source = dialogNavigation,
            serializer = ArticleDetailsComponent.ArticleDetailsConfig.serializer(),
            handleBackButton = true,
        ) { config, childComponentContext ->
        articleDetailsComponentFactory.create(
            context = childComponentContext,
            articleId = config.articleId
        )
    }

    override fun toggleArticleReadStatus(articleId: String) =
        storeInstance.accept(
            intent = LatestNewsStore.Intent.ToggleArticleReadStatus(
                articleId = articleId,
            )
        )

    override fun toggleArticleFavoriteStatus(articleId: String) =
        storeInstance.accept(
            intent = LatestNewsStore.Intent.ToggleArticleFavoriteStatus(
                articleId = articleId,
            )
        )

    override fun searchByTitle(query: String?) =
        storeInstance.accept(LatestNewsStore.Intent.SearchFieldChange(query))

    override fun filterByCategory(category: NewsCategory?) =
        storeInstance.accept(LatestNewsStore.Intent.SelectCategory(category))

    override fun showArticleDetails(articleId: String) {
        dialogNavigation.activate(
            ArticleDetailsComponent.ArticleDetailsConfig(articleId)
        )
    }

    override fun hideArticleDetails() {
        dialogNavigation.dismiss()
    }
}