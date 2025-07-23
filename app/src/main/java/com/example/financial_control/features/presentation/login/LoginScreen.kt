package com.example.financial_control.features.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.financial_control.R
import com.example.financial_control.design_system.components.SHPrimaryButton

@Composable
fun LoginScreen(viewModel: LoginContract = LoginViewModel()) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 80.dp)
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(74.dp),
            )
            Text(
                text = "Financial Control",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(innerPadding)
                    .wrapContentSize()
            )
            Text(
                text = "Bem-vindo ao  Financial Control!\nGerencie suas despesas de forma simples e segura.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(innerPadding)
                    .wrapContentSize()
            )
            Spacer(modifier = Modifier.weight(1f))
            SHPrimaryButton(
                title = "entrar com a sua conta Google",
                iconPainter = R.drawable.google_logo,
                color = MaterialTheme.colorScheme.primary,
                onColor = MaterialTheme.colorScheme.onPrimary,
                font = MaterialTheme.typography.bodyMedium,
                isLoading = false,
                onClick = { viewModel.onLoginClicked() }
            )
        }
    }
}