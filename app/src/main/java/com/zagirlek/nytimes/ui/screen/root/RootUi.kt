package com.zagirlek.nytimes.ui.screen.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.zagirlek.nytimes.ui.screen.auth.AuthUi
import com.zagirlek.nytimes.ui.screen.main.main.MainUi
import com.zagirlek.nytimes.ui.screen.splash.SplashScreen

@Composable
fun RootUi(rootComponent: RootComponent) {
    Children(
        stack = rootComponent.stack
    ) {
        when(val child = it.instance){
            is RootComponent.Child.Splash -> SplashScreen(component = child.component)
            is RootComponent.Child.Login -> AuthUi(component = child.component)
            is RootComponent.Child.Main -> MainUi(component = child.component)
        }
    }
}