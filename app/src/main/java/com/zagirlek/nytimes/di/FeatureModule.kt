package com.zagirlek.nytimes.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.articledetails.di.ArticleDetailsFeatureModule
import com.zagirlek.auth.di.AuthDomainModule
import com.zagirlek.auth.di.AuthFeatureModule
import com.zagirlek.favorite.di.FavoriteNewsFeatureModule
import com.zagirlek.latest.di.LatestNewsFeatureModule
import com.zagirlek.news.di.NewsDomainModule
import com.zagirlek.nytimes.ui.main.di.MainModule
import com.zagirlek.splash.di.SplashFeatureModule
import com.zagirlek.weather.di.WeatherDomainModule
import com.zagirlek.weather.di.WeatherFeatureModule

class FeatureModule(
    private val authDomainModule: AuthDomainModule,
    private val weatherDomainModule: WeatherDomainModule,
    private val newsDomainModule: NewsDomainModule,
    private val storeFactory: StoreFactory
) {
    fun getMainModule(): MainModule = MainModule(
        weatherFeatureModule = WeatherFeatureModule(weatherDomainModule),
        latestNewsFeatureModule = getLatestNewsModule(),
        favoriteNewsFeatureModule = getFavoriteNewsModule()
    )

    fun getArticleDetailsModule() = ArticleDetailsFeatureModule(
        storeFactory = storeFactory,
        newsDomainModule = newsDomainModule
    )

    fun getLatestNewsModule(): LatestNewsFeatureModule = LatestNewsFeatureModule(
        storeFactory = storeFactory,
        articleDetailsModule = getArticleDetailsModule(),
        newsDomainModule = newsDomainModule
    )

    fun getFavoriteNewsModule(): FavoriteNewsFeatureModule = FavoriteNewsFeatureModule(
        storeFactory = storeFactory,
        articleDetailsModule = getArticleDetailsModule(),
        newsDomainModule = newsDomainModule
    )

    fun getSplashModule(): SplashFeatureModule = SplashFeatureModule(
        getCurrentAuthTokenUseCase = authDomainModule.provideGetCurrentAuthTokenUseCase()
    )

    fun getAuthModule(): AuthFeatureModule = AuthFeatureModule(
        storeFactory = storeFactory,
        authUseCase = authDomainModule.provideAuthUseCase(),
        authWithoutLoginUseCase = authDomainModule.provideAuthWithoutLoginUseCase()
    )
}