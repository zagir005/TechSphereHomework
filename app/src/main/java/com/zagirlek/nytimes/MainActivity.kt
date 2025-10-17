package com.zagirlek.nytimes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.zagirlek.nytimes.ui.root.RootUi
import com.zagirlek.nytimes.ui.root.components.DefaultRootComponent
import com.zagirlek.ui.theme.NyTimesTheme

class MainActivity : ComponentActivity() {

    val app: NyTimesApp by lazy {
        application as NyTimesApp
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val defaultRootComponent = DefaultRootComponent(
            componentContext = defaultComponentContext(),
            mainModule = app.rootModule.getMainModule(),
            splashFeatureModule = app.rootModule.getSplashModule(),
            authFeatureModule = app.rootModule.getAuthModule()
        )


        setContent {
            NyTimesTheme {
                RootUi(defaultRootComponent)
            }
        }
    }
}