package com.zagirlek.nytimes.ui.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.zagirlek.auth.AuthScreen
import com.zagirlek.nytimes.ui.main.main.MainComponent
import com.zagirlek.splash.SplashComponent

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed class Child{
        data class Splash(val component: SplashComponent): Child()
        data class Login(val component: AuthScreen): Child()
        data class Main(val component: MainComponent): Child()
    }
}