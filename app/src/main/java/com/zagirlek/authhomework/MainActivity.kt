package com.zagirlek.authhomework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.zagirlek.authhomework.ui.screen.loading.SplashUi
import com.zagirlek.authhomework.ui.screen.root.RootComponent
import com.zagirlek.authhomework.ui.screen.root.RootUi
import com.zagirlek.authhomework.ui.theme.AuthHomeworkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val rootComponent = RootComponent(defaultComponentContext())

        setContent {
            AuthHomeworkTheme(darkTheme = true) {
                RootUi(rootComponent)
            }
        }
    }
}