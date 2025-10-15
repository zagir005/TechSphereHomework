package com.zagirlek.nytimes.ui.main.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.nytimes.domain.usecase.news.FavoriteNewsFlowUseCase
import com.zagirlek.nytimes.domain.usecase.news.GetArticleFullFlowUseCase
import com.zagirlek.nytimes.domain.usecase.news.LatestNewsPagingUseCase
import com.zagirlek.nytimes.domain.usecase.news.ToggleArticleFavoriteStatusUseCase
import com.zagirlek.nytimes.domain.usecase.news.ToggleArticleReadStatusUseCase
import com.zagirlek.nytimes.domain.usecase.weather.AddWeatherPointUseCase
import com.zagirlek.nytimes.domain.usecase.weather.DeleteWeatherPointUseCase
import com.zagirlek.nytimes.domain.usecase.weather.GetCityAutocompleteUseCase
import com.zagirlek.nytimes.domain.usecase.weather.GetOrPutCityUseCase
import com.zagirlek.nytimes.domain.usecase.weather.GetRecentCityListUseCase
import com.zagirlek.nytimes.domain.usecase.weather.GetWeatherPointsHistoryFlowUseCase
import com.zagirlek.nytimes.ui.main.news.articledetails.ArticleDetailsComponent
import com.zagirlek.nytimes.ui.main.news.articledetails.cmp.ArticleDetailsComponentFactory
import com.zagirlek.nytimes.ui.main.news.articledetails.cmp.DefaultArticleDetailsComponent
import com.zagirlek.nytimes.ui.main.news.favorite.FavoriteNewsScreen
import com.zagirlek.nytimes.ui.main.news.favorite.cmp.FavoriteNewsScreenComponent
import com.zagirlek.nytimes.ui.main.news.latest.LatestNewsScreen
import com.zagirlek.nytimes.ui.main.news.latest.cmp.LatestNewsScreenComponent
import com.zagirlek.nytimes.ui.main.weather.WeatherComponent
import com.zagirlek.nytimes.ui.main.weather.cmp.DefaultWeatherComponent

class MainModule(
    private val storeFactory: StoreFactory,
    private val getCityAutocompleteUseCase: GetCityAutocompleteUseCase,
    private val getWeatherPointsHistoryFlowUseCase: GetWeatherPointsHistoryFlowUseCase,
    private val getRecentCityListUseCase: GetRecentCityListUseCase,
    private val deleteWeatherPointUseCase: DeleteWeatherPointUseCase,
    private val addWeatherPointUseCase: AddWeatherPointUseCase,
    private val getOrPutCityUseCase: GetOrPutCityUseCase,
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
    ): WeatherComponent =
        DefaultWeatherComponent(
            componentContext = componentContext,
            getCityAutocompleteUseCase = getCityAutocompleteUseCase,
            getWeatherPointsHistoryFlowUseCase = getWeatherPointsHistoryFlowUseCase,
            getRecentCityListUseCase = getRecentCityListUseCase,
            deleteWeatherPointUseCase = deleteWeatherPointUseCase,
            addWeatherPointUseCase = addWeatherPointUseCase,
            getOrPutCityUseCase = getOrPutCityUseCase
        )

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