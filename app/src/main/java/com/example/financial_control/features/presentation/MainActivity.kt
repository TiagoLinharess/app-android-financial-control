package com.example.financial_control.features.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.financial_control.features.presentation.login.LoginScreen
import com.example.financial_control.ui.theme.FinancialControlTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinancialControlTheme {
                LoginScreen()
            }
        }
    }
}