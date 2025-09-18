package com.zagirlek.nytimes.ui.screen.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.zagirlek.nytimes.ui.screen.login.LoginUi
import com.zagirlek.nytimes.ui.screen.main.main.MainUi
import com.zagirlek.nytimes.ui.screen.splash.SplashUi

@Composable
fun RootUi(rootComponent: RootComponent) {
    Children(
        stack = rootComponent.stack
    ) {
        when(val child = it.instance){
            is RootComponent.Child.Splash -> SplashUi(child.component)
            is RootComponent.Child.Login -> LoginUi(child.component)
            is RootComponent.Child.Main -> MainUi(child.component)
        }
    }
}