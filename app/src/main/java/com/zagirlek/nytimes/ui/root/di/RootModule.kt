package com.zagirlek.nytimes.ui.root.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.auth.di.AuthDomainModule
import com.zagirlek.auth.di.AuthFeatureModule
import com.zagirlek.nytimes.domain.UseCaseModule
import com.zagirlek.nytimes.ui.main.di.MainModule
import com.zagirlek.splash.di.SplashFeatureModule
import com.zagirlek.weather.di.WeatherDomainModule
import com.zagirlek.weather.di.WeatherFeatureModule

class RootModule(
    private val authDomainModule: AuthDomainModule,
    private val weatherDomainModule: WeatherDomainModule,
    private val useCaseModule: UseCaseModule,
    private val storeFactory: StoreFactory
) {
    fun getMainModule(): MainModule = MainModule(
        storeFactory = storeFactory,
        weatherFeatureModule = WeatherFeatureModule(weatherDomainModule),
        latestNewsPagingUseCase = useCaseModule.getLatestNewsPagingUseCase(),
        favoriteNewsFlowUseCase = useCaseModule.getFavoriteNewsFlowUseCase(),
        toggleArticleFavoriteStatusUseCase = useCaseModule.toggleArticleFavoriteStatusUseCase(),
        toggleArticleReadStatusUseCase = useCaseModule.toggleArticleReadStatusUseCase(),
        getArticleFullFlowUseCase = useCaseModule.getArticleFullByIdFlowUseCase(),
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