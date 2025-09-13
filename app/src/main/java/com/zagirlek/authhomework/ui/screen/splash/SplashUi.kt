package com.zagirlek.authhomework.ui.screen.splash

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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zagirlek.authhomework.BuildConfig
import com.zagirlek.authhomework.R
import com.zagirlek.authhomework.ui.components.SpinningLoader
import com.zagirlek.authhomework.ui.screen.root.components.SplashComponent
import com.zagirlek.authhomework.ui.screen.splash.cmp.state.SplashAction
import com.zagirlek.authhomework.ui.screen.splash.cmp.state.SplashMutation
import com.zagirlek.authhomework.ui.theme.robotoFlexFamily
import kotlinx.coroutines.delay


@Composable
fun SplashUi(
    splashComponent: SplashComponent,
) {
    val state by splashComponent.state.subscribeAsState()

    var animVisibility by remember { mutableStateOf(false) }
    val animDuration = 200

    LaunchedEffect(state) {
        when (state) {
            SplashMutation.Loading -> animVisibility = true
            SplashMutation.OnFinish -> {
                animVisibility = false
                delay(animDuration.toLong())
                splashComponent.action(SplashAction.Finish)
            }
        }
    }


    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
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
                        durationMillis = animDuration,
                        easing = FastOutSlowInEasing
                    ),
                    initialOffsetY = { it }
                ) + expandVertically(expandFrom = Alignment.Top) + fadeIn(initialAlpha = 0.3f)
            ) {
                Text(
                    text = stringResource(R.string.app_name),
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
                    durationMillis = animDuration,
                    easing = FastOutSlowInEasing
                ),
                initialOffsetY = { it }
            ) + expandVertically(expandFrom = Alignment.Top) + fadeIn(initialAlpha = 0.3f),
        ) {
            Text(
                text = BuildConfig.VERSION_NAME,
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = robotoFlexFamily
            )
        }
    }
}