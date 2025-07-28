package com.example.financial_control.features.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.financial_control.R
import com.example.financial_control.features.presentation.side_menu.SideMenu

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    val viewModel: HomeViewModel = hiltViewModel()

    SideMenu(navController = navController) { innerPadding ->
        Text(
            text = stringResource(R.string.module_home),
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}