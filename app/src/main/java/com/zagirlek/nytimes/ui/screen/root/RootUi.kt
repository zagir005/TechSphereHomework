package com.zagirlek.nytimes.ui.screen.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.zagirlek.nytimes.ui.screen.login.LoginScreen
import com.zagirlek.nytimes.ui.screen.main.main.MainUi
import com.zagirlek.nytimes.ui.screen.splash.SplashScreen

@Composable
fun RootUi(rootComponent: RootComponent) {
    Children(
        stack = rootComponent.stack
    ) {
        Scaffold { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ){
                when(val child = it.instance){
                    is RootComponent.Child.Splash -> SplashScreen(component = child.component)
                    is RootComponent.Child.Login -> LoginScreen(component = child.component)
                    is RootComponent.Child.Main -> MainUi(component = child.component)
                }
            }
        }
    }
}