package com.zagirlek.nytimes.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.zagirlek.auth.AuthScreen
import com.zagirlek.root.AdminRootComponent
import com.zagirlek.root.ClientRootComponent
import com.zagirlek.splash.SplashComponent

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>
    sealed class Child{
        data class Splash(val component: SplashComponent): Child()
        data class Login(val component: AuthScreen): Child()
        data class ClientRoot(val component: ClientRootComponent): Child()
        data class AdminRoot(val component: AdminRootComponent): Child()
    }
}