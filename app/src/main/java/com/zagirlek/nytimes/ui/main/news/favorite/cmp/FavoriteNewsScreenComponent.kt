package com.zagirlek.nytimes.ui.main.news.favorite.cmp

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
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.zagirlek.common.model.NewsCategory
import com.zagirlek.common.utils.getStore
import com.zagirlek.nytimes.domain.usecase.news.FavoriteNewsFlowUseCase
import com.zagirlek.nytimes.domain.usecase.news.ToggleArticleFavoriteStatusUseCase
import com.zagirlek.nytimes.ui.main.news.articledetails.ArticleDetailsComponent
import com.zagirlek.nytimes.ui.main.news.articledetails.cmp.ArticleDetailsComponentFactory
import com.zagirlek.nytimes.ui.main.news.favorite.FavoriteNewsScreen
import com.zagirlek.nytimes.ui.main.news.favorite.model.FavoriteNewsModel
import com.zagirlek.nytimes.ui.main.news.favorite.model.toModel
import com.zagirlek.nytimes.ui.main.news.favorite.store.FavoriteNewsStore
import com.zagirlek.nytimes.ui.main.news.favorite.store.FavoriteNewsStoreFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavoriteNewsScreenComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val articleDetailsComponentFactory: ArticleDetailsComponentFactory,
    private val favoriteNewsFlowUseCase: FavoriteNewsFlowUseCase,
    private val toggleFavoriteStatusUseCase: ToggleArticleFavoriteStatusUseCase,
): FavoriteNewsScreen, ComponentContext by componentContext {
    private val componentScope = coroutineScope(
        context = SupervisorJob() + Dispatchers.Main.immediate
    ).also {
        doOnDestroy { it.cancel() }
    }

    private val storeInstance = instanceKeeper.getStore {
        FavoriteNewsStoreFactory(
            storeFactory = storeFactory,
            favoriteNewsFlow = favoriteNewsFlowUseCase,
            toggleFavorite = toggleFavoriteStatusUseCase
        ).create()
    }

    override val model: StateFlow<FavoriteNewsModel> = storeInstance
        .stateFlow(lifecycle)
        .map {
            it.toModel()
        }
        .stateIn(
            scope = componentScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FavoriteNewsModel()
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

    override fun toggleArticleFavoriteStatus(articleId: String) =
        storeInstance.accept(
            intent = FavoriteNewsStore.Intent.ToggleArticleFavoriteStatus(
                articleId = articleId,
            )
        )

    override fun searchByTitle(query: String?) =
        storeInstance.accept(FavoriteNewsStore.Intent.SearchFieldChange(query))

    override fun filterByCategory(category: NewsCategory?) =
        storeInstance.accept(FavoriteNewsStore.Intent.SelectCategory(category))

    override fun showArticleDetails(articleId: String) {
        dialogNavigation.activate(
            ArticleDetailsComponent.ArticleDetailsConfig(articleId)
        )
    }

    override fun hideArticleDetails() {
        dialogNavigation.dismiss()
    }
}