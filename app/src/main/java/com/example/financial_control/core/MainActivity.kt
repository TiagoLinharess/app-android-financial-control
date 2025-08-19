package com.example.financial_control.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.example.financial_control.features.presentation.start.StartScreen
import com.example.financial_control.router.AppNavHost
import com.example.financial_control.ui.theme.FinancialControlTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FinancialControlTheme {
                val navController = rememberNavController()
                var showStartScreen by remember { mutableStateOf(true) }

                if (showStartScreen) {
                    StartScreen {
                        showStartScreen = false
                    }
                } else {
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}