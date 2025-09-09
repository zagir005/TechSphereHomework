package com.zagirlek.authhomework.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagirlek.authhomework.ui.theme.AuthHomeworkTheme
import kotlinx.coroutines.delay
import kotlin.math.min

@Composable
fun SpinningLoader(modifier: Modifier = Modifier) {
    var startAnimation by remember { mutableStateOf(false) }

    val transition = if (startAnimation) {
        rememberInfiniteTransition()
    } else {
        null
    }

    val animatedIndex by transition?.animateFloat(
        initialValue = 0f,
        targetValue = 12f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    ) ?: remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        delay(200)
        startAnimation = true
    }

    Canvas(modifier = modifier) {
        val radius = min(size.width, size.height) / 2f
        val stickHeight = radius / 2f
        val stickWidth = size.width / 7f

        val alphas = List(12) { i ->
            1f - (i * (1f / 12))
        }

        for (i in 0 until 12){
            val angle = (360 / 12 * i) - 180f
            val offset = animatedIndex.toInt() % 12
            val alphaForThisStick = alphas[(i - offset + 12) % 12]

            rotate(
                degrees = angle,
                pivot = Offset(size.width / 2, size.height / 2)
            ){
                drawRoundRect(
                    color = Color.White.copy(alpha = alphaForThisStick),
                    cornerRadius = CornerRadius(44f,44f),
                    size = Size(stickWidth, stickHeight),
                    topLeft = Offset(x = (size.width / 2) - stickWidth / 2, y = size.height - stickHeight)
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun SpinningLoaderPreview(modifier: Modifier = Modifier) {
    AuthHomeworkTheme {
        SpinningLoader(
            modifier = Modifier.size(200.dp)
        )
    }
}