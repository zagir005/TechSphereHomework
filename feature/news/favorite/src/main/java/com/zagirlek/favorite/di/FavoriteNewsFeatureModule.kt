package com.zagirlek.favorite.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.articledetails.di.ArticleDetailsFeatureModule
import com.zagirlek.favorite.FavoriteNewsScreen
import com.zagirlek.favorite.cmp.FavoriteNewsScreenComponent
import com.zagirlek.news.di.NewsDomainModule

class FavoriteNewsFeatureModule(
    private val storeFactory: StoreFactory,
    private val articleDetailsModule: ArticleDetailsFeatureModule,
    private val newsDomainModule: NewsDomainModule
) {
    fun getFavoriteNewsComponent(componentContext: ComponentContext): FavoriteNewsScreen =
        FavoriteNewsScreenComponent(
            componentContext = componentContext,
            storeFactory = storeFactory,
            articleDetailsModule = articleDetailsModule,
            favoriteNewsFlowUseCase = newsDomainModule.provideFavoriteNewsFlowUseCase(),
            toggleFavoriteStatusUseCase = newsDomainModule.provideToggleArticleFavoriteStatusUseCase()
        )
}