package com.zagirlek.nytimes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.zagirlek.nytimes.ui.screen.root.RootUi
import com.zagirlek.nytimes.ui.screen.root.components.DefaultRootComponent
import com.zagirlek.nytimes.core.ui.theme.NyTimesTheme

class MainActivity : ComponentActivity() {

    val app: NyTimesApp by lazy {
        application as NyTimesApp
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val defaultRootComponent = DefaultRootComponent(
            componentContext = defaultComponentContext(),
            authModule = app.rootModule.getAuthModule(),
            mainModule = app.rootModule.getMainModule(),
            splashModule = app.rootModule.getSplashModule()
        )

        setContent {
            NyTimesTheme {
                RootUi(defaultRootComponent)
            }
        }
    }
}