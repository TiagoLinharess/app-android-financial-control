package com.example.financial_control.features.presentation.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financial_control.domain.error.FCError
import com.example.financial_control.domain.models.UserModel
import com.example.financial_control.domain.repository.AuthRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepositoryInterface
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _authError = MutableStateFlow<Int?>(null)
    val authError: StateFlow<Int?> = _authError

    fun signIn(context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _authError.value = null
                authRepository.signIn(context)
            } catch (e: Exception) {
                val error = FCError.fromApiString(e.message)
                _authError.value = error.getMessageId()
            } finally {
                _isLoading.value = false
            }
        }
    }
}