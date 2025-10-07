package com.zagirlek.nytimes.ui.main.news

import androidx.paging.map
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
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.core.utils.getStore
import com.zagirlek.nytimes.domain.usecase.news.GetArticleFullByIdFlowUseCase
import com.zagirlek.nytimes.domain.usecase.news.GetPagingFavoriteNewsUseCase
import com.zagirlek.nytimes.domain.usecase.news.GetPagingNewsUseCase
import com.zagirlek.nytimes.domain.usecase.news.ToggleArticleFavoriteStatusUseCase
import com.zagirlek.nytimes.domain.usecase.news.ToggleArticleReadStatusUseCase
import com.zagirlek.nytimes.ui.main.articledetails.ArticleDetailsComponent
import com.zagirlek.nytimes.ui.main.articledetails.cmp.DefaultArticleDetailsComponent
import com.zagirlek.nytimes.ui.main.news.model.NewsModel
import com.zagirlek.nytimes.ui.main.news.model.NewsSideEffect
import com.zagirlek.nytimes.ui.main.news.model.toArticleItem
import com.zagirlek.nytimes.ui.main.news.model.toModel
import com.zagirlek.nytimes.ui.main.news.store.NewsStore
import com.zagirlek.nytimes.ui.main.news.store.NewsStoreFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.Serializable

class NewsScreenComponent(
    componentContext: ComponentContext,
    private val favoriteListMode: Boolean,
    private val storeFactory: StoreFactory,
    private val getPagingNewsUseCase: GetPagingNewsUseCase,
    private val getPagingFavoriteNewsUseCase: GetPagingFavoriteNewsUseCase,
    private val toggleFavoriteStatusUseCase: ToggleArticleFavoriteStatusUseCase,
    private val toggleReadStatusUseCase: ToggleArticleReadStatusUseCase,
    private val getArticleFullByIdFlowUseCase: GetArticleFullByIdFlowUseCase
): ComponentContext by componentContext, NewsScreen {
    private val componentScope = coroutineScope(
        context = SupervisorJob() + Dispatchers.Main.immediate
    ).also {
        doOnDestroy { it.cancel() }
    }

    private val storeInstance = instanceKeeper.getStore {
        NewsStoreFactory(
            storeFactory = storeFactory,
            loadPagingNews = { category, query ->
                if (favoriteListMode)
                    getPagingFavoriteNewsUseCase(category, query)
                        .map {
                            it.map {
                                article -> article.toArticleItem()
                            }
                        }
                else
                    getPagingNewsUseCase(category,query)
                        .map {
                            it.map {
                                article -> article.toArticleItem()
                            }
                        }
            },
            toggleReadStatusUseCase = toggleReadStatusUseCase,
            toggleFavoriteStatusUseCase = toggleFavoriteStatusUseCase
        ).create()
    }

    override val sideEffect: SharedFlow<NewsSideEffect> = storeInstance
        .labels
        .map {
            when(it){
                is NewsStore.Label.ShowError -> NewsSideEffect.ShowSnackbar(it.msgRes)
            }
        }
        .filterNotNull()
        .shareIn(
            scope = componentScope,
            started = SharingStarted.WhileSubscribed(),
            replay = 0
        )

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

    private val dialogNavigation = SlotNavigation<ArticleDetailsModalSheetConfig>()
    override val dialog: Value<ChildSlot<*, ArticleDetailsComponent>> = childSlot(
            source = dialogNavigation,
            serializer = ArticleDetailsModalSheetConfig.serializer(),
            handleBackButton = true,
        ) { config, childComponentContext ->
        DefaultArticleDetailsComponent(
            componentContext = componentContext,
            articleId = config.articleId,
            onErrorClose = {
                storeInstance.accept(NewsStore.Intent.ShowError(it))
                hideArticleDetails()
            },
            getArticleFullByIdFlowUseCase = getArticleFullByIdFlowUseCase,
            toggleReadStatusUseCase = toggleReadStatusUseCase,
            toggleFavoriteStatusUseCase = toggleFavoriteStatusUseCase
        )
    }

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

    override fun showArticleDetails(articleId: String) {
        dialogNavigation.activate(
            ArticleDetailsModalSheetConfig(articleId)
        )
    }

    override fun hideArticleDetails() {
        dialogNavigation.dismiss()
    }

    @Serializable
    private data class ArticleDetailsModalSheetConfig(
        val articleId: String
    )
}