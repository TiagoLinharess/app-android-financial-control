package com.example.financial_control.features.presentation.start

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.financial_control.R
import com.example.financial_control.ui.theme.DefaultDimensions
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

@Composable
fun StartScreen(
    onAnimationEnd: () -> Unit
) {
    val imageOffsetY = remember { Animatable(0f) }
    val imageAlpha = remember { Animatable(1f) }
    val jumpHeight = -60f
    val numberOfJumps = 3
    val totalJumpsDurationMillis = 1200
    val singleJumpDurationMillis = (totalJumpsDurationMillis / (numberOfJumps * 2))
    val exitAnimationDurationMillis = 700
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }
    val imageSizePx = with(density) { DefaultDimensions.xxGiant.toPx() }
    val exitTargetY = remember { -( (screenHeightPx / 2) + (imageSizePx / 2) + 50f ) }


    LaunchedEffect(key1 = Unit) {
        for (i in 0 until numberOfJumps) {
            imageOffsetY.animateTo(
                targetValue = jumpHeight,
                animationSpec = tween(
                    durationMillis = singleJumpDurationMillis,
                    easing = EaseInOutCubic
                )
            )
            imageOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    durationMillis = singleJumpDurationMillis,
                    easing = EaseInOutCubic
                )
            )
        }

        delay(100)

        coroutineScope {
            async {
                imageOffsetY.animateTo(
                    targetValue = exitTargetY,
                    animationSpec = tween(
                        durationMillis = exitAnimationDurationMillis,
                        easing = EaseInCubic
                    )
                )
            }
            async {
                imageAlpha.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = exitAnimationDurationMillis,
                        easing = EaseInCubic
                    )
                )
            }
        }
        onAnimationEnd()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
    ) { _ ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .graphicsLayer(
                        alpha = imageAlpha.value,
                        translationY = imageOffsetY.value,
                        clip = false
                    )
                    .size(DefaultDimensions.xxGiant)
            )
        }
    }
}