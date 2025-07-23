package com.example.financial_control.design_system.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun SHPrimaryButton(
    title: String,
    iconPainter: Int? = null,
    color: Color,
    onColor: Color,
    font: TextStyle,
    isLoading: Boolean = false,
    isDisabled: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            if (isLoading) return@Button
            onClick()
        },
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = onColor,
            disabledContainerColor = color.copy(alpha = 0.5f),
            disabledContentColor = onColor.copy(alpha = 0.7f)
        ),
        enabled = !isDisabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp),
                    color = onColor,
                    strokeWidth = 2.dp
                )
            } else {
                if (iconPainter != null) {
                    Image(
                        painter = painterResource(iconPainter),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = title,
                    style = font,
                    color = onColor,
                    modifier = Modifier
                )
            }
        }
    }
}