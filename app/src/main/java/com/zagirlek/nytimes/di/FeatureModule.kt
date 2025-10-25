package com.zagirlek.nytimes.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.addoredit.di.AddOrEditUserFeatureModule
import com.zagirlek.articledetails.di.ArticleDetailsFeatureModule
import com.zagirlek.auth.di.AuthDomainModule
import com.zagirlek.auth.di.AuthFeatureModule
import com.zagirlek.favorite.di.FavoriteNewsFeatureModule
import com.zagirlek.home.di.DashboardHomeFeatureModule
import com.zagirlek.latest.di.LatestNewsFeatureModule
import com.zagirlek.list.di.ComputersListFeatureModule
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
    /* == ROOT MODULES == */
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

    /* == ADMIN == */
    fun getDashboardRootFeatureModule(): DashboardRootFeatureModule = DashboardRootFeatureModule(
        dashboardHomeModule = getDashboardHomeModule(),
        computersListModule = getComputersListModule()
    )
    fun getDashboardHomeModule(): DashboardHomeFeatureModule = DashboardHomeFeatureModule(
        storeFactory = storeFactory
    )
    fun getComputersListModule(): ComputersListFeatureModule = ComputersListFeatureModule(
        storeFactory = storeFactory
    )
    fun getSplashModule(): SplashFeatureModule = SplashFeatureModule(
        getCurrentAuthTokenUseCase = authDomainModule.provideGetCurrentAuthTokenUseCase()
    )
    fun getUserListFeatureModule(): UserListFeatureModule = UserListFeatureModule(
        storeFactory = storeFactory,
        userDomainModule = userDomainModule,
        addOrEditUserFeatureModule = addOrEditUserFeatureModule()
    )

    fun addOrEditUserFeatureModule(): AddOrEditUserFeatureModule = AddOrEditUserFeatureModule (
        storeFactory = storeFactory,
        userDomainModule = userDomainModule
    )

    /* == CLIENT == */

    fun getAuthModule(): AuthFeatureModule = AuthFeatureModule(
        storeFactory = storeFactory,
        authUseCase = authDomainModule.provideAuthUseCase(),
        authWithoutLoginUseCase = authDomainModule.provideAuthWithoutLoginUseCase(),
        logoutUseCase = authDomainModule.provideLogoutUseCase()
    )

    fun getWeatherModule(): WeatherFeatureModule = WeatherFeatureModule(
        weatherDomainModule
    )
    fun getArticleDetailsModule(): ArticleDetailsFeatureModule = ArticleDetailsFeatureModule(
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
}