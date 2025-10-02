package com.zagirlek.nytimes.ui.screen.root.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.nytimes.domain.usecase.di.UseCaseModule
import com.zagirlek.nytimes.ui.screen.auth.di.AuthModule
import com.zagirlek.nytimes.ui.screen.main.di.MainModule
import com.zagirlek.nytimes.ui.screen.splash.di.SplashModule

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
        getPagingNewsUseCase = useCaseModule.getPagingNewsUseCase(),
        updateArticleStatusUseCase = useCaseModule.updateArticleStatusUseCase(),
        getArticleStatusFlowUseCase = useCaseModule.getArticleLocalInfoFlowUseCase()
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