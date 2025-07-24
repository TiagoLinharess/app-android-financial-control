package com.example.financial_control.features.presentation

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.financial_control.features.presentation.login.GoogleAuthClient
import com.example.financial_control.features.presentation.login.LoginScreen
import com.example.financial_control.ui.theme.FinancialControlTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val googleAuthClient = GoogleAuthClient(applicationContext)

        setContent {
            FinancialControlTheme {

                var isSignedIn by rememberSaveable {
                    mutableStateOf(googleAuthClient.isUserSignedIn())
                }

                if (isSignedIn) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = MaterialTheme.colorScheme.background,
                    ) { innerPadding ->
                        OutlinedButton(
                            modifier = Modifier.padding(innerPadding),
                            onClick = {
                            lifecycleScope.launch {
                                googleAuthClient.signOut()
                                isSignedIn = false
                            }
                        }) {
                            Text(text = "Sign out")
                        }
                    }
                } else {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = MaterialTheme.colorScheme.background,
                    ) { innerPadding ->
                        OutlinedButton(
                            modifier = Modifier.padding(innerPadding),
                            onClick = {
                            lifecycleScope.launch {
                                isSignedIn = googleAuthClient.signIn()
                            }
                        }) {
                            Text(text = "Sign in")
                        }
                    }
                }
            }
        }
    }
}