package com.example.financial_control.features.presentation.side_menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.financial_control.R
import com.example.financial_control.domain.models.side_menu.SideMenuItemModel
import com.example.financial_control.ui.theme.DefaultDimensions

@Composable
fun SideMenuItem(item: SideMenuItemModel, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(DefaultDimensions.small),
        modifier = Modifier
            .height(DefaultDimensions.xxLarge),
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(DefaultDimensions.extraSmall)
        ) {
            Image(
                painter = painterResource(id = item.iconPainter),
                contentDescription = null,
                modifier = Modifier.size(DefaultDimensions.xBig),
                colorFilter = ColorFilter.tint(
                    MaterialTheme.colorScheme.onBackground
                )
            )
            Text(
                text = stringResource(item.titleId),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.chevron_right),
                contentDescription = null,
                modifier = Modifier.size(DefaultDimensions.xBig),
                colorFilter = ColorFilter.tint(
                    MaterialTheme.colorScheme.onBackground
                )
            )
        }
    }
}