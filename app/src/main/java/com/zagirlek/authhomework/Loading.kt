package com.zagirlek.authhomework

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zagirlek.authhomework.ui.theme.robotoFlexFamily

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Scaffold { paddingValues ->
        var animVisibility by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            animVisibility = true
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
                Image(
                    painter = painterResource(id = R.drawable.spinner_main_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp),
                    alignment = Alignment.Center
                )

                AnimatedVisibility(
                    visible = animVisibility,
                    enter = slideInVertically(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        ),
                        initialOffsetY = { -100 }
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
                        durationMillis = 300,
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