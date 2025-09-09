package com.zagirlek.authhomework.ui.screen.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.zagirlek.authhomework.ui.screen.loading.cmp.SplashComponent
import com.zagirlek.authhomework.ui.screen.login.cmp.LoginComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable

class RootComponent(
    componentContext: ComponentContext
): ComponentContext by componentContext {

    private val nav = StackNavigation<Config>()

    val state: Value<ChildStack<*, Child>> =
        childStack(
            source = nav,
            initialConfiguration = Config.Splash,
            serializer = Config.serializer(),
            handleBackButton = true,
            childFactory = ::componentChild
        )

    @OptIn(DelicateDecomposeApi::class)
    private fun componentChild(config: Config, component: ComponentContext): Child {
        return when(config){
            Config.Splash -> splashChild(component)
            Config.Login -> loginChild(component)
        }
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun splashChild(component: ComponentContext): Child.SplashChild = Child.SplashChild(
        splashChild = SplashComponent(
            componentContext = component,
            mainContext = Dispatchers.Main
        ) {
            nav.push(Config.Login)
        }
    )

    private fun loginChild(component: ComponentContext): Child.LoginChild = Child.LoginChild(
        loginChild = LoginComponent(
            componentContext = component
        )
    )

    sealed class Child{
        data class SplashChild(val splashChild: SplashComponent): Child()

        data class LoginChild(val loginChild: LoginComponent): Child()
    }


    @Serializable
    sealed class Config{
        @Serializable
        data object Splash: Config()

        @Serializable
        data object Login: Config()
    }
}