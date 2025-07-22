package com.example.financial_control.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    tertiary = OnBackgroundBodyDark,
    onTertiary = OnBackgroundWeakDark,
    primary = BrandDark,
    onPrimary = OnBrandDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    error = ErrorDark,
    onError = OnErrorDark,
    errorContainer = WarningDark,
    onErrorContainer = OnWarningDark,
    outline = SuccessDark,
    outlineVariant = OnSurfaceDark
)

private val LightColorScheme = lightColorScheme(
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    tertiary = OnBackgroundBodyLight,
    onTertiary = OnBackgroundWeakLight,
    primary = BrandLight,
    onPrimary = OnBrandLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    error = ErrorLight,
    onError = OnErrorLight,
    errorContainer = WarningLight,
    onErrorContainer = OnWarningLight,
    outline = SuccessLight,
    outlineVariant = OnSurfaceLight
)

@Composable
fun FinancialControlTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}