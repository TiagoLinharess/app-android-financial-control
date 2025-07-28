package com.example.financial_control.features.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financial_control.R
import com.example.financial_control.design_system.components.SHPrimaryButton
import com.example.financial_control.design_system.components.SHToastStyle
import com.example.financial_control.design_system.components.SHoast
import com.example.financial_control.ui.theme.DefaultDimensions

@Composable
fun LoginScreen() {
    val context = LocalContext.current
    val viewModel: LoginViewModel = hiltViewModel()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val error by viewModel.authError.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = DefaultDimensions.superGiant)
                .padding(horizontal = DefaultDimensions.small)
                .padding(bottom = DefaultDimensions.small),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(DefaultDimensions.nano)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(DefaultDimensions.xxGiant),
            )
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(innerPadding)
            )
            Text(
                text = stringResource(R.string.login_description),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(innerPadding)
            )
            Spacer(modifier = Modifier.weight(1f))
            SHPrimaryButton(
                title = stringResource(R.string.login_button),
                iconPainter = R.drawable.google_logo,
                color = MaterialTheme.colorScheme.primary,
                onColor = MaterialTheme.colorScheme.onPrimary,
                font = MaterialTheme.typography.bodyMedium,
                isLoading = isLoading,
                onClick = { viewModel.signIn(context) }
            )
        }
        error?.let {
            SHoast(
                style = SHToastStyle.ERROR,
                font = MaterialTheme.typography.bodyMedium,
                message = stringResource(it)
            )
        }
    }
}