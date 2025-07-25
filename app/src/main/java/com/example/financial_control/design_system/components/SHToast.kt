package com.example.financial_control.design_system.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import com.example.financial_control.R
import com.example.financial_control.ui.theme.DefaultDimensions
import kotlinx.coroutines.delay

enum class SHToastStyle {
    ERROR,
    WARNING,
    SUCCESS,
    INFO;

    @Composable
    fun backgroundColor(): androidx.compose.ui.graphics.Color {
        return when (this) {
            ERROR -> MaterialTheme.colorScheme.error
            WARNING -> MaterialTheme.colorScheme.errorContainer
            SUCCESS -> MaterialTheme.colorScheme.outline
            INFO -> MaterialTheme.colorScheme.primary
        }
    }

    @Composable
    fun textColor(): androidx.compose.ui.graphics.Color {
        return when (this) {
            ERROR -> MaterialTheme.colorScheme.onError
            WARNING -> MaterialTheme.colorScheme.onErrorContainer
            SUCCESS -> MaterialTheme.colorScheme.outlineVariant
            INFO -> MaterialTheme.colorScheme.onPrimary
        }
    }

    @Composable
    fun image(): Int {
        return when (this) {
            ERROR -> R.drawable.error
            WARNING -> R.drawable.warning
            SUCCESS -> R.drawable.success
            INFO -> R.drawable.info
        }
    }
}

@Composable
fun SHoast(
    style: SHToastStyle,
    font: TextStyle,
    message: String,
    durationMillis: Long = 3000
) {
    var isVisible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(durationMillis)
        if (isVisible) {
            isVisible = false
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = DefaultDimensions.superGiant)
                .padding(DefaultDimensions.small),
            contentAlignment = Alignment.TopCenter
        ) {
            Surface(
                color = style.backgroundColor(),
                shape = RoundedCornerShape(percent = 50),
                tonalElevation = DefaultDimensions.extraSmall
            ) {
                Row(
                    modifier = Modifier
                        .padding(DefaultDimensions.small)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(DefaultDimensions.small)
                ) {
                    Image(
                        painter = painterResource(id = style.image()),
                        contentDescription = null,
                        modifier = Modifier.size(DefaultDimensions.big),
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(style.textColor())
                    )
                    Text(
                        text = message,
                        maxLines = 3,
                        style = font,
                        color = style.textColor(),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}