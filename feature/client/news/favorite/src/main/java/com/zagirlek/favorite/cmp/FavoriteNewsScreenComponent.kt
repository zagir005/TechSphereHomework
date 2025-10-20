package com.zagirlek.favorite.cmp

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
import com.zagirlek.articledetails.ArticleDetailsComponent
import com.zagirlek.articledetails.di.ArticleDetailsFeatureModule
import com.zagirlek.common.model.NewsCategory
import com.zagirlek.common.utils.getStore
import com.zagirlek.favorite.FavoriteNewsScreen
import com.zagirlek.favorite.model.FavoriteNewsModel
import com.zagirlek.favorite.model.toModel
import com.zagirlek.favorite.store.FavoriteNewsStore
import com.zagirlek.favorite.store.FavoriteNewsStoreFactory
import com.zagirlek.news.usecase.FavoriteNewsFlowUseCase
import com.zagirlek.news.usecase.ToggleArticleFavoriteStatusUseCase
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
    private val articleDetailsModule: ArticleDetailsFeatureModule,
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
        articleDetailsModule.getArticleDetailsComponent (
            componentContext = childComponentContext,
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