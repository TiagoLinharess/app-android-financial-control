package com.example.financial_control.features.presentation

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.financial_control.features.presentation.login.AuthViewModel
import com.example.financial_control.features.presentation.login.LoginScreen
import com.example.financial_control.ui.theme.FinancialControlTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch

@HiltAndroidApp
class MyFinancialApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FinancialControlTheme {
                val viewModel: AuthViewModel = hiltViewModel()
                val showLogin by viewModel.showLogin.collectAsStateWithLifecycle()

                if (showLogin) {
                    LoginScreen()
                } else {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = MaterialTheme.colorScheme.background,
                    ) { innerPadding ->
                        OutlinedButton(
                            modifier = Modifier.padding(innerPadding),
                            onClick = {
                                lifecycleScope.launch {
                                    viewModel.signOut()
                                }
                            }) {
                            Text(text = "Sign out")
                        }
                    }
                }
            }
        }
    }
}