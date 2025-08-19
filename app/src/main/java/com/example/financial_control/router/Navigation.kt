package com.example.financial_control.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.financial_control.features.presentation.categories.CategoryListScreen
import com.example.financial_control.features.presentation.home.HomeScreen
import com.example.financial_control.features.presentation.login.LoginScreen
import com.example.financial_control.features.presentation.login_loading.LoginLoadingScreen

enum class AppScreen {
    LOGIN,
    LOGIN_LOADING,
    HOME,
    CATEGORIES
}

sealed class NavigationItem(val route: String) {
    data object Home: NavigationItem(AppScreen.HOME.name)
    data object Login: NavigationItem(AppScreen.LOGIN.name)
    data object LoginLoading: NavigationItem(AppScreen.LOGIN_LOADING.name)
    data object Categories: NavigationItem(AppScreen.CATEGORIES.name)
}

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    val viewModel: NavigationViewModel = hiltViewModel()
    val showLogin by viewModel.showLogin.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = NavigationItem.LoginLoading.route
    ) {
        composable(NavigationItem.Login.route) {
            LoginScreen()
        }
        composable(NavigationItem.LoginLoading.route) {
            LoginLoadingScreen()
        }
        composable(NavigationItem.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(NavigationItem.Categories.route) {
            CategoryListScreen(navController = navController)
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