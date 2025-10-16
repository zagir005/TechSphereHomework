package com.zagirlek.nytimes.ui.root.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.zagirlek.nytimes.ui.auth.AuthScreen
import com.zagirlek.nytimes.ui.auth.di.AuthModule
import com.zagirlek.nytimes.ui.main.di.MainModule
import com.zagirlek.nytimes.ui.main.main.MainComponent
import com.zagirlek.nytimes.ui.main.main.cmp.DefaultMainComponent
import com.zagirlek.nytimes.ui.root.RootComponent
import com.zagirlek.nytimes.ui.splash.SplashComponent
import com.zagirlek.nytimes.ui.splash.di.SplashModule
import kotlinx.serialization.Serializable


class DefaultRootComponent(
    private val componentContext: ComponentContext,
    private val authModule: AuthModule,
    private val mainModule: MainModule,
    private val splashModule: SplashModule
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
        splashModule.getSplashComponent(
            componentContext = componentContext,
            toAuth = { navigation.replaceCurrent(Config.Auth) },
            toMain = { navigation.replaceCurrent(Config.Main) }
        )

    private fun auth(componentContext: ComponentContext): AuthScreen =
        authModule.getAuthComponent(
            componentContext = componentContext,
            toMain = { navigation.replaceCurrent(Config.Main) }
        )

    private fun main(componentContext: ComponentContext): MainComponent =
        DefaultMainComponent(
            componentContext = componentContext,
            mainModule = mainModule
        )

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