package com.zagirlek.nytimes.ui.screen.root.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.zagirlek.nytimes.ui.screen.root.RootComponent
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.zagirlek.nytimes.domain.repository.AuthRepository
import com.zagirlek.nytimes.ui.screen.login.LoginComponent
import com.zagirlek.nytimes.ui.screen.login.cmp.DefaultLoginComponent
import com.zagirlek.nytimes.ui.screen.main.main.MainComponent
import com.zagirlek.nytimes.ui.screen.main.main.cmp.DefaultMainComponent
import com.zagirlek.nytimes.ui.screen.splash.SplashComponent
import com.zagirlek.nytimes.ui.screen.splash.cmp.DefaultSplashComponent
import kotlinx.serialization.Serializable


class DefaultRootComponent(
    private val componentContext: ComponentContext,
    private val authRepository: AuthRepository
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
            Config.Login -> RootComponent.Child.Login(login(componentContext))
            Config.Main -> RootComponent.Child.Main(main(componentContext))
        }
    }

    private fun splash(componentContext: ComponentContext): SplashComponent =
        DefaultSplashComponent(
            componentContext = componentContext,
            onAuthRequired = { navigation.replaceCurrent(Config.Login) },
            onAuthSuccess = { navigation.replaceCurrent(Config.Main) },
            authRepository = authRepository
        )

    private fun login(componentContext: ComponentContext): LoginComponent =
        DefaultLoginComponent(
            componentContext = componentContext,
            onAuth = { navigation.replaceCurrent(Config.Main) }
        )

    private fun main(componentContext: ComponentContext): MainComponent =
        DefaultMainComponent(
            componentContext
        )

    @Serializable
    private sealed class Config{
        @Serializable
        object Splash: Config()

        @Serializable
        object Login: Config()

        @Serializable
        object Main: Config()
    }
}