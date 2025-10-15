package com.zagirlek.nytimes.ui.root.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.nytimes.domain.usecase.di.UseCaseModule
import com.zagirlek.nytimes.ui.auth.di.AuthModule
import com.zagirlek.nytimes.ui.main.di.MainModule
import com.zagirlek.nytimes.ui.splash.di.SplashModule

class RootModule(
    private val useCaseModule: UseCaseModule,
    private val storeFactory: StoreFactory
) {
    fun getMainModule(): MainModule = MainModule(
        storeFactory = storeFactory,
        getCityAutocompleteUseCase = useCaseModule.getCityAutocompleteUseCase(),
        getWeatherPointsHistoryFlowUseCase = useCaseModule.getWeatherPointsHistoryFlowUseCase(),
        getRecentCityListUseCase = useCaseModule.getRecentCityListUseCase(),
        deleteWeatherPointUseCase = useCaseModule.deleteWeatherPointUseCase(),
        addWeatherPointUseCase = useCaseModule.addWeatherPointUseCase(),
        getOrPutCityUseCase = useCaseModule.getOrPutCityUseCase(),
        latestNewsPagingUseCase = useCaseModule.getLatestNewsPagingUseCase(),
        favoriteNewsFlowUseCase = useCaseModule.getFavoriteNewsFlowUseCase(),
        toggleArticleFavoriteStatusUseCase = useCaseModule.toggleArticleFavoriteStatusUseCase(),
        toggleArticleReadStatusUseCase = useCaseModule.toggleArticleReadStatusUseCase(),
        getArticleFullFlowUseCase = useCaseModule.getArticleFullByIdFlowUseCase(),
    )

    fun getSplashModule(): SplashModule = SplashModule(
        getCurrentAuthTokenUseCase = useCaseModule.getCurrentAuthTokenUseCase()
    )

    fun getAuthModule(): AuthModule = AuthModule(
        storeFactory = storeFactory,
        authUseCase = useCaseModule.authUseCase(),
        authWithoutLoginUseCase = useCaseModule.authWithoutLoginUseCase()
    )
}