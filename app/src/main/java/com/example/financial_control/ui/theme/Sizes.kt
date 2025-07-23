package com.example.financial_control.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class AppDimensions(
    var zero: Dp = 0.dp,
    val one: Dp = 1.dp,
    val two: Dp = 2.dp,
    val three: Dp = 3.dp,
    val nano: Dp = 4.dp,
    val extraSmall: Dp = 8.dp,
    val smaller: Dp = 12.dp,
    val small: Dp = 16.dp,
    val medium: Dp = 20.dp,
    val big: Dp = 24.dp,
    val xBig: Dp = 28.dp,
    val xxBig: Dp = 32.dp,
    val xxxBig: Dp = 36.dp,
    val superBig: Dp = 40.dp,
    val large: Dp = 44.dp,
    val xLarge: Dp = 48.dp,
    val xxLarge: Dp = 52.dp,
    val xxxLarge: Dp = 56.dp,
    val superLarge: Dp = 60.dp,
    val giant: Dp = 64.dp,
    val xGiant: Dp = 68.dp,
    val xxGiant: Dp = 72.dp,
    val xxxGiant: Dp = 76.dp,
    val superGiant: Dp = 80.dp,
)

val DefaultDimensions = AppDimensions()

val LocalDimensions = staticCompositionLocalOf { DefaultDimensions }