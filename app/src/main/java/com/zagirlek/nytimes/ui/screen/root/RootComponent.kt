package com.zagirlek.nytimes.ui.screen.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.zagirlek.nytimes.ui.screen.login.LoginComponent
import com.zagirlek.nytimes.ui.screen.main.main.MainComponent
import com.zagirlek.nytimes.ui.screen.splash.SplashComponent

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed class Child{
        data class Splash(val component: SplashComponent): Child()
        data class Login(val component: LoginComponent): Child()
        data class Main(val component: MainComponent): Child()
    }
}