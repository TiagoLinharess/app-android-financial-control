package com.example.financial_control.features.presentation.side_menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.financial_control.R
import com.example.financial_control.design_system.components.SHPrimaryButton
import com.example.financial_control.router.NavigationItem
import com.example.financial_control.ui.theme.DefaultDimensions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SideMenu(
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    val viewModel: SideMenuViewModel = hiltViewModel()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.background
            ) {
                Row(
                    modifier = Modifier
                        .padding(DefaultDimensions.small)
                ) {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = null,
                            modifier = Modifier.size(DefaultDimensions.xBig),
                            colorFilter = ColorFilter.tint(
                                MaterialTheme.colorScheme.onBackground
                            )
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
                Column(
                    modifier = Modifier
                        .padding(DefaultDimensions.small),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(DefaultDimensions.small)
                ) {
                    Text(
                        text = stringResource(R.string.commons_menu),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    viewModel.items.forEachIndexed { index, item ->
                        SideMenuItem(item = item) {
                            scope.launch {
                                navController.navigate(NavigationItem.Categories.route)
                                drawerState.close()
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    SHPrimaryButton(
                        title = stringResource(R.string.commons_sign_out),
                        color = MaterialTheme.colorScheme.error,
                        onColor = MaterialTheme.colorScheme.onError,
                        font = MaterialTheme.typography.bodyMedium,
                        isLoading = isLoading
                    ) { viewModel.signOut() }
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.module_home),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }) {
                            Image(
                                painter = painterResource(id = R.drawable.menu),
                                contentDescription = null,
                                modifier = Modifier.size(DefaultDimensions.xBig),
                                colorFilter = ColorFilter.tint(
                                    MaterialTheme.colorScheme.onBackground
                                )
                            )
                        }
                    },
                    colors = TopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        scrolledContainerColor = MaterialTheme.colorScheme.background,
                        navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                        titleContentColor = MaterialTheme.colorScheme.onBackground,
                        actionIconContentColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}