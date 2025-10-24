package com.zagirlek.nytimes.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.zagirlek.auth.AuthScreen
import com.zagirlek.auth.di.AuthFeatureModule
import com.zagirlek.nytimes.root.RootComponent.Child.AdminRoot
import com.zagirlek.nytimes.root.RootComponent.Child.ClientRoot
import com.zagirlek.nytimes.root.RootComponent.Child.Login
import com.zagirlek.nytimes.root.RootComponent.Child.Splash
import com.zagirlek.root.AdminRootComponent
import com.zagirlek.root.ClientRootComponent
import com.zagirlek.root.di.AdminRootFeatureModule
import com.zagirlek.root.di.ClientRootFeatureModule
import com.zagirlek.splash.SplashComponent
import com.zagirlek.splash.di.SplashFeatureModule
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    private val componentContext: ComponentContext,
    private val splashFeatureModule: SplashFeatureModule,
    private val authFeatureModule: AuthFeatureModule,
    private val clientRootFeatureModule: ClientRootFeatureModule,
    private val adminRootFeatureModule: AdminRootFeatureModule,
): ComponentContext by componentContext, RootComponent {
    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Splash,
        handleBackButton = true,
        childFactory = ::child
    )
    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child{
        return when(config){
            Config.Splash -> Splash(splash(componentContext))
            Config.Auth -> Login(auth(componentContext))
            Config.ClientRoot -> ClientRoot(client(componentContext))
            Config.AdminRoot -> AdminRoot(admin(componentContext))
        }
    }
    private fun splash(componentContext: ComponentContext): SplashComponent =
        splashFeatureModule.getSplashComponent(
            componentContext = componentContext,
            toAuth = { navigation.replaceCurrent(Config.Auth) },
            toAdmin = { navigation.replaceCurrent(Config.AdminRoot) },
            toClient = { navigation.replaceCurrent(Config.ClientRoot) }
        )
    private fun auth(componentContext: ComponentContext): AuthScreen =
        authFeatureModule.getAuthComponent(
            componentContext = componentContext,
            toClient = { navigation.replaceCurrent(Config.ClientRoot) },
            toAdmin = { navigation.replaceCurrent(Config.AdminRoot) }
        )
    private fun client(componentContext: ComponentContext): ClientRootComponent =
        clientRootFeatureModule.getMainComponent(
            componentContext = componentContext,
            onLogout = {
                navigation.replaceCurrent(Config.Auth)
            }
        )

    private fun admin(componentContext: ComponentContext): AdminRootComponent =
        adminRootFeatureModule.getAdminRootComponent(componentContext = componentContext)

    @Serializable
    private sealed class Config{
        @Serializable
        object Splash: Config()
        @Serializable
        object Auth: Config()
        @Serializable
        object ClientRoot: Config()
        @Serializable
        object AdminRoot: Config()
    }
}