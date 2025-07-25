package com.example.financial_control.core

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financial_control.features.presentation.login.AuthViewModel
import com.example.financial_control.features.presentation.login.LoginScreen
import com.example.financial_control.ui.theme.FinancialControlTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

object AppScreen {
    const val LOGIN = "login"
    const val HOME = "home"
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
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = if (showLogin) AppScreen.LOGIN else AppScreen.HOME
                ) {
                    composable(AppScreen.LOGIN) {
                        LoginScreen()
                    }
                    composable(AppScreen.HOME) {
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

                LaunchedEffect(showLogin, navController) {
                    val currentRoute = navController.currentBackStackEntry?.destination?.route
                    if (!showLogin && currentRoute == AppScreen.LOGIN) {
                        navController.navigate(AppScreen.HOME) {
                            popUpTo(AppScreen.LOGIN) { inclusive = true }
                            launchSingleTop = true
                        }
                    } else if (showLogin && currentRoute == AppScreen.HOME) {
                        navController.navigate(AppScreen.LOGIN) {
                            popUpTo(AppScreen.HOME) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
    }
}