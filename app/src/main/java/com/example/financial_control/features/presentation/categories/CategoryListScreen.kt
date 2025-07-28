package com.example.financial_control.features.presentation.categories


import com.example.financial_control.R
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.financial_control.design_system.components.SHContainer

@Composable
fun CategoryListScreen(
    navController: NavHostController
) {
    SHContainer(title = stringResource(R.string.module_categories), navController = navController) { innerPadding ->
        Text(
            text = stringResource(R.string.module_categories),
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}