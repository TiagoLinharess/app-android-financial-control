package com.example.financial_control.features.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financial_control.features.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val currentUser: StateFlow<FirebaseUser?> = authRepository.currentUser
    val isLoading: StateFlow<Boolean> = authRepository.isLoading
    val authError: StateFlow<String?> = authRepository.authError

    val showLogin: StateFlow<Boolean> = combine(currentUser, isLoading) { user, loading ->
        user == null
    }.stateIn(
        viewModelScope,
        kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        true
    )

    fun signIn() {
        viewModelScope.launch {
            authRepository.signIn()
        }
    }

    suspend fun signOut() {
        authRepository.signOut()
    }
}