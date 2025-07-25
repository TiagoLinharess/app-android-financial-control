package com.example.financial_control.router

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.financial_control.features.presentation.login.AuthViewModel
import com.example.financial_control.features.presentation.login.LoginScreen

enum class AppScreen {
    LOGIN,
    HOME
}

sealed class NavigationItem(val route: String) {
    data object Home: NavigationItem(AppScreen.HOME.name)
    data object Login: NavigationItem(AppScreen.LOGIN.name)
}

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    val viewModel: AuthViewModel = hiltViewModel()
    val showLogin by viewModel.showLogin.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = if (showLogin) NavigationItem.Login.route else NavigationItem.Home.route
    ) {
        composable(NavigationItem.Login.route) {
            LoginScreen()
        }
        composable(NavigationItem.Home.route) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = MaterialTheme.colorScheme.background,
            ) { innerPadding ->
                OutlinedButton(
                    modifier = Modifier.padding(innerPadding),
                    onClick = { viewModel.signOut() }) {
                    Text(text = "Sign out")
                }
            }
        }
    }

    LaunchedEffect(showLogin, navController) {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        if (!showLogin && currentRoute != NavigationItem.Home.route) {
            navController.navigate(NavigationItem.Home.route) {
                popUpTo(NavigationItem.Home.route) { inclusive = true }
                launchSingleTop = true
            }
        } else if (showLogin && currentRoute != NavigationItem.Login.route) {
            navController.navigate(NavigationItem.Login.route) {
                popUpTo(NavigationItem.Login.route) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
}