package com.example.financial_control.features.presentation.home

import androidx.lifecycle.ViewModel
import com.example.financial_control.domain.repository.AuthRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepositoryInterface
) : ViewModel() {

}