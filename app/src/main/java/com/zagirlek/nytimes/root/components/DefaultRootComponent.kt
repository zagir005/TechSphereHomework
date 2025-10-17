package com.zagirlek.nytimes.root.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.zagirlek.auth.AuthScreen
import com.zagirlek.auth.di.AuthFeatureModule
import com.zagirlek.main.MainComponent
import com.zagirlek.main.di.MainFeatureModule
import com.zagirlek.nytimes.root.RootComponent
import com.zagirlek.splash.SplashComponent
import com.zagirlek.splash.di.SplashFeatureModule
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    private val componentContext: ComponentContext,
    private val splashFeatureModule: SplashFeatureModule,
    private val authFeatureModule: AuthFeatureModule,
    private val mainModule: MainFeatureModule,
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
            Config.Splash -> RootComponent.Child.Splash(splash(componentContext))
            Config.Auth -> RootComponent.Child.Login(auth(componentContext))
            Config.Main -> RootComponent.Child.Main(main(componentContext))
        }
    }

    private fun splash(componentContext: ComponentContext): SplashComponent =
        splashFeatureModule.getSplashComponent(
            componentContext = componentContext,
            toAuth = { navigation.replaceCurrent(Config.Auth) },
            toMain = { navigation.replaceCurrent(Config.Main) }
        )

    private fun auth(componentContext: ComponentContext): AuthScreen =
        authFeatureModule.getAuthComponent(
            componentContext = componentContext,
            toMain = { navigation.replaceCurrent(Config.Main) }
        )

    private fun main(componentContext: ComponentContext): MainComponent =
        mainModule.getMainComponent(componentContext = componentContext)

    @Serializable
    private sealed class Config{
        @Serializable
        object Splash: Config()

        @Serializable
        object Auth: Config()

        @Serializable
        object Main: Config()
    }
}