package com.zagirlek.latest.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.articledetails.di.ArticleDetailsFeatureModule
import com.zagirlek.latest.LatestNewsScreen
import com.zagirlek.latest.cmp.LatestNewsScreenComponent
import com.zagirlek.news.di.NewsDomainModule

class LatestNewsFeatureModule(
    private val storeFactory: StoreFactory,
    private val articleDetailsModule: ArticleDetailsFeatureModule,
    private val newsDomainModule: NewsDomainModule
) {
    fun getLatestNewsComponent(componentContext: ComponentContext): LatestNewsScreen =
        LatestNewsScreenComponent(
            componentContext = componentContext,
            storeFactory = storeFactory,
            articleDetailsModule = articleDetailsModule,
            latestNewsPagingUseCase = newsDomainModule.provideLatestNewsPagingUseCase(),
            toggleFavoriteStatusUseCase = newsDomainModule.provideToggleArticleFavoriteStatusUseCase(),
            toggleReadStatusUseCase = newsDomainModule.provideToggleArticleReadStatusUseCase(),
        )
}