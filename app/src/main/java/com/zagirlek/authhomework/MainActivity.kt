package com.zagirlek.authhomework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.defaultComponentContext
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
                Scaffold(
                    containerColor = Color.Black
                ) { paddingValues ->
                    RootUi(rootComponent, modifier = Modifier.padding(paddingValues))
                }

            }
        }
    }
}