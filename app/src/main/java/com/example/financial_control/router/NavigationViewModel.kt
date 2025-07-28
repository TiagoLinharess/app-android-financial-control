package com.example.financial_control.router

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financial_control.domain.models.UserModel
import com.example.financial_control.domain.repository.AuthRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val authRepository: AuthRepositoryInterface
) : ViewModel() {

    val currentUser: StateFlow<UserModel?> = authRepository.currentUser

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val showLogin: StateFlow<Boolean> = combine(currentUser, isLoading) { user, _ ->
        user == null
    }.stateIn(
        viewModelScope,
        kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        true
    )
}