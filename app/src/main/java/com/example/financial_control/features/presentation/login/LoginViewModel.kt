package com.example.financial_control.features.presentation.login

import androidx.lifecycle.ViewModel

interface LoginContract {
    fun onLoginClicked()
}

class LoginViewModel : ViewModel(), LoginContract {

    override fun onLoginClicked() {
        print("onLoginClicked")
    }
}