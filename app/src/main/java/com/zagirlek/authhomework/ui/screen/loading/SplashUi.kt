package com.zagirlek.authhomework.ui.screen.loading

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zagirlek.authhomework.ui.components.SpinningLoader
import com.zagirlek.authhomework.ui.screen.loading.cmp.SplashAction
import com.zagirlek.authhomework.ui.screen.loading.cmp.SplashComponent
import com.zagirlek.authhomework.ui.screen.loading.cmp.SplashMutation
import com.zagirlek.authhomework.ui.theme.robotoFlexFamily
import kotlinx.coroutines.delay


@Composable
fun SplashUi(
    splashComponent: SplashComponent,
    modifier: Modifier = Modifier
) {
    Scaffold { paddingValues ->
        val state by splashComponent.state.subscribeAsState()

        var animVisibility by remember { mutableStateOf(false) }

        when(state){
            SplashMutation.Loading -> {
                LaunchedEffect(Unit) {
                    animVisibility = true
                }
            }
            SplashMutation.OnFinish -> {
                LaunchedEffect(Unit) {
                    animVisibility = false
                    delay(200)
                    splashComponent.action(SplashAction.Finish)
                }
            }
        }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {

                SpinningLoader(
                    modifier = Modifier.size(80.dp)
                )
                AnimatedVisibility(
                    visible = animVisibility,
                    enter = slideInVertically(
                        animationSpec = tween(
                            durationMillis = 200,
                            easing = FastOutSlowInEasing
                        ),
                        initialOffsetY = { it }
                    ) + expandVertically(expandFrom = Alignment.Top) + fadeIn(initialAlpha = 0.3f)
                ) {
                    Text(
                        text = "NYTIMES",
                        color = Color.White,
                        fontSize = 40.sp,
                        fontFamily = robotoFlexFamily
                    )
                }
            }

            AnimatedVisibility(
                visible = animVisibility,
                enter = slideInVertically(
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = FastOutSlowInEasing
                    ),
                    initialOffsetY = { +100 }
                ) + expandVertically(expandFrom = Alignment.Top) + fadeIn(initialAlpha = 0.3f),
            ) {
                Text(
                    text = "1.0.41",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = robotoFlexFamily
                )
            }
        }
    }
}