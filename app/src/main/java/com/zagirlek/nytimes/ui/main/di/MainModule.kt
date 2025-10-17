package com.zagirlek.nytimes.ui.main.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.news.usecase.FavoriteNewsFlowUseCase
import com.zagirlek.news.usecase.GetArticleFullFlowUseCase
import com.zagirlek.news.usecase.LatestNewsPagingUseCase
import com.zagirlek.news.usecase.ToggleArticleFavoriteStatusUseCase
import com.zagirlek.news.usecase.ToggleArticleReadStatusUseCase
import com.zagirlek.nytimes.ui.main.news.articledetails.ArticleDetailsComponent
import com.zagirlek.nytimes.ui.main.news.articledetails.cmp.ArticleDetailsComponentFactory
import com.zagirlek.nytimes.ui.main.news.articledetails.cmp.DefaultArticleDetailsComponent
import com.zagirlek.nytimes.ui.main.news.favorite.FavoriteNewsScreen
import com.zagirlek.nytimes.ui.main.news.favorite.cmp.FavoriteNewsScreenComponent
import com.zagirlek.nytimes.ui.main.news.latest.LatestNewsScreen
import com.zagirlek.nytimes.ui.main.news.latest.cmp.LatestNewsScreenComponent
import com.zagirlek.weather.WeatherScreen
import com.zagirlek.weather.di.WeatherFeatureModule

class MainModule(
    private val storeFactory: StoreFactory,
    private val weatherFeatureModule: WeatherFeatureModule,
    private val latestNewsPagingUseCase: LatestNewsPagingUseCase,
    private val favoriteNewsFlowUseCase: FavoriteNewsFlowUseCase,
    private val toggleArticleFavoriteStatusUseCase: ToggleArticleFavoriteStatusUseCase,
    private val toggleArticleReadStatusUseCase: ToggleArticleReadStatusUseCase,
    private val getArticleFullFlowUseCase: GetArticleFullFlowUseCase
) {
    val articleDetailsComponentFactory = object : ArticleDetailsComponentFactory {
        override fun create(context: ComponentContext, articleId: String): ArticleDetailsComponent {
            return DefaultArticleDetailsComponent(
                componentContext = context,
                storeFactory = storeFactory,
                articleId = articleId,
                getArticleFullFlowUseCase = getArticleFullFlowUseCase,
                toggleFavoriteStatusUseCase = toggleArticleFavoriteStatusUseCase,
                toggleReadStatusUseCase = toggleArticleReadStatusUseCase
            )
        }
    }
    fun getWeatherComponent(
        componentContext: ComponentContext
    ): WeatherScreen =
        weatherFeatureModule.getWeatherComponent(componentContext)

    fun getLatestNewsComponent(
        componentContext: ComponentContext,
    ): LatestNewsScreen =
        LatestNewsScreenComponent(
            componentContext = componentContext,
            storeFactory = storeFactory,
            latestNewsPagingUseCase = latestNewsPagingUseCase,
            toggleFavoriteStatusUseCase = toggleArticleFavoriteStatusUseCase,
            toggleReadStatusUseCase = toggleArticleReadStatusUseCase,
            articleDetailsComponentFactory = articleDetailsComponentFactory
        )

    fun getFavoriteNewsComponent(
        componentContext: ComponentContext,
    ): FavoriteNewsScreen =
        FavoriteNewsScreenComponent(
            componentContext = componentContext,
            storeFactory = storeFactory,
            favoriteNewsFlowUseCase = favoriteNewsFlowUseCase,
            toggleFavoriteStatusUseCase = toggleArticleFavoriteStatusUseCase,
            articleDetailsComponentFactory = articleDetailsComponentFactory
        )
}