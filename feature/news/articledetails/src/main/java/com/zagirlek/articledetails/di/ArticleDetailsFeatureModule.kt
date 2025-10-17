package com.zagirlek.articledetails.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.articledetails.ArticleDetailsComponent
import com.zagirlek.articledetails.cmp.DefaultArticleDetailsComponent
import com.zagirlek.news.di.NewsDomainModule

class ArticleDetailsFeatureModule(
    private val storeFactory: StoreFactory,
    private val newsDomainModule: NewsDomainModule
) {
    fun getArticleDetailsComponent(
        componentContext: ComponentContext,
        articleId: String
    ): ArticleDetailsComponent =
        DefaultArticleDetailsComponent(
            componentContext = componentContext,
            articleId = articleId,
            storeFactory = storeFactory,
            getArticleFullFlowUseCase = newsDomainModule.provideGetArticleFullFlowUseCase(),
            toggleFavoriteStatusUseCase = newsDomainModule.provideToggleArticleFavoriteStatusUseCase(),
            toggleReadStatusUseCase = newsDomainModule.provideToggleArticleReadStatusUseCase()
        )

}