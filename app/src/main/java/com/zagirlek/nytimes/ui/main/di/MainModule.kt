package com.zagirlek.nytimes.ui.main.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.nytimes.domain.usecase.news.GetArticleFullByIdFlowUseCase
import com.zagirlek.nytimes.domain.usecase.news.GetPagingNewsUseCase
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
import com.zagirlek.nytimes.ui.main.news.latest.LatestNewsScreen
import com.zagirlek.nytimes.ui.main.news.latest.LatestNewsScreenComponent
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
    private val getPagingNewsUseCase: GetPagingNewsUseCase,
    private val toggleArticleFavoriteStatusUseCase: ToggleArticleFavoriteStatusUseCase,
    private val toggleArticleReadStatusUseCase: ToggleArticleReadStatusUseCase,
    private val getArticleFullByIdFlowUseCase: GetArticleFullByIdFlowUseCase
) {
    val articleDetailsComponentFactory = object : ArticleDetailsComponentFactory {
        override fun create(context: ComponentContext, articleId: String): ArticleDetailsComponent {
            return DefaultArticleDetailsComponent(
                componentContext = context,
                storeFactory = storeFactory,
                articleId = articleId,
                getArticleFullByIdFlowUseCase = getArticleFullByIdFlowUseCase,
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
        LatestNewsScreenComponent (
            componentContext = componentContext,
            storeFactory = storeFactory,
            getPagingNewsUseCase = getPagingNewsUseCase,
            toggleFavoriteStatusUseCase = toggleArticleFavoriteStatusUseCase,
            toggleReadStatusUseCase = toggleArticleReadStatusUseCase,
            articleDetailsComponentFactory = articleDetailsComponentFactory
        )
}