package com.zagirlek.nytimes.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.addoredit.di.AddOrEditUserFeatureModule
import com.zagirlek.articledetails.di.ArticleDetailsFeatureModule
import com.zagirlek.auth.di.AuthDomainModule
import com.zagirlek.auth.di.AuthFeatureModule
import com.zagirlek.favorite.di.FavoriteNewsFeatureModule
import com.zagirlek.latest.di.LatestNewsFeatureModule
import com.zagirlek.list.di.UserListFeatureModule
import com.zagirlek.news.di.NewsDomainModule
import com.zagirlek.root.di.AdminRootFeatureModule
import com.zagirlek.root.di.ClientRootFeatureModule
import com.zagirlek.root.di.DashboardRootFeatureModule
import com.zagirlek.splash.di.SplashFeatureModule
import com.zagirlek.user.di.UserDomainModule
import com.zagirlek.weather.di.WeatherDomainModule
import com.zagirlek.weather.di.WeatherFeatureModule

class FeatureModule(
    private val authDomainModule: AuthDomainModule,
    private val weatherDomainModule: WeatherDomainModule,
    private val newsDomainModule: NewsDomainModule,
    private val userDomainModule: UserDomainModule,
    private val storeFactory: StoreFactory
) {
    fun getClientRootModule(): ClientRootFeatureModule = ClientRootFeatureModule(
        weatherFeatureModule = getWeatherModule(),
        latestNewsFeatureModule = getLatestNewsModule(),
        favoriteNewsFeatureModule = getFavoriteNewsModule(),
        getCurrentUserUseCase = authDomainModule.provideGetCurrentUserUseCase()
    )
    fun getAdminRootModule(): AdminRootFeatureModule = AdminRootFeatureModule(
        dashboardRootFeatureModule = getDashboardRootFeatureModule(),
        userListFeatureModule = getUserListFeatureModule()
    )
    fun getSplashModule(): SplashFeatureModule = SplashFeatureModule(
        getCurrentAuthTokenUseCase = authDomainModule.provideGetCurrentAuthTokenUseCase()
    )
    fun getAuthModule(): AuthFeatureModule = AuthFeatureModule(
        storeFactory = storeFactory,
        authUseCase = authDomainModule.provideAuthUseCase(),
        authWithoutLoginUseCase = authDomainModule.provideAuthWithoutLoginUseCase(),
        logoutUseCase = authDomainModule.provideLogoutUseCase()
    )

    private fun getDashboardRootFeatureModule(): DashboardRootFeatureModule = DashboardRootFeatureModule()
    private fun getUserListFeatureModule(): UserListFeatureModule = UserListFeatureModule(
        storeFactory = storeFactory,
        userDomainModule = userDomainModule,
        addOrEditUserFeatureModule = addOrEditUserFeatureModule()
    )

    private fun addOrEditUserFeatureModule(): AddOrEditUserFeatureModule = AddOrEditUserFeatureModule (
        storeFactory = storeFactory,
        userDomainModule = userDomainModule
    )
    private fun getWeatherModule(): WeatherFeatureModule = WeatherFeatureModule(
        weatherDomainModule
    )
    private fun getArticleDetailsModule(): ArticleDetailsFeatureModule = ArticleDetailsFeatureModule(
        storeFactory = storeFactory,
        newsDomainModule = newsDomainModule
    )
    private fun getLatestNewsModule(): LatestNewsFeatureModule = LatestNewsFeatureModule(
        storeFactory = storeFactory,
        articleDetailsModule = getArticleDetailsModule(),
        newsDomainModule = newsDomainModule
    )
    private fun getFavoriteNewsModule(): FavoriteNewsFeatureModule = FavoriteNewsFeatureModule(
        storeFactory = storeFactory,
        articleDetailsModule = getArticleDetailsModule(),
        newsDomainModule = newsDomainModule
    )
}