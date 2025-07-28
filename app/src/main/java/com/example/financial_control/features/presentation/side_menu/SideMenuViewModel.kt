package com.example.financial_control.features.presentation.side_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financial_control.domain.models.side_menu.SideMenuItemModel
import com.example.financial_control.domain.repository.AuthRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SideMenuViewModel @Inject constructor(
    private val authRepository: AuthRepositoryInterface
) : ViewModel() {


    val items: List<SideMenuItemModel> = SideMenuItemModel.items
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun signOut() {
        viewModelScope.launch {
            _isLoading.value = true
            authRepository.signOut()
            _isLoading.value = false
        }
    }
}