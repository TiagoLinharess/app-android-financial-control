package com.example.financial_control.features.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financial_control.domain.models.UserModel
import com.example.financial_control.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val currentUser: StateFlow<UserModel?> = authRepository.currentUser

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError

    val showLogin: StateFlow<Boolean> = combine(currentUser, isLoading) { user, _ ->
        user == null
    }.stateIn(
        viewModelScope,
        kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        true
    )

    fun signIn() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _authError.value = null
                authRepository.signIn()
            } catch (e: Exception) {
                _authError.value = e.message ?: "Firebase Google sign-in failed"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            _isLoading.value = true
            authRepository.signOut()
            _isLoading.value = false
        }
    }
}