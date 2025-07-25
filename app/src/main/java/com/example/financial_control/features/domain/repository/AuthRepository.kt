package com.example.financial_control.features.domain.repository

import com.example.financial_control.features.domain.client.GoogleAuthClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val googleAuthClient: GoogleAuthClient
) {
    private val _currentUser = MutableStateFlow(firebaseAuth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError

    init {
        firebaseAuth.addAuthStateListener { auth ->
            _currentUser.value = auth.currentUser
            if (auth.currentUser == null) {
                _authError.value = null
            }
        }
    }

    suspend fun signIn() {
        _isLoading.value = true
        _authError.value = null
        try {
            val credential = googleAuthClient.getCredentialResponse()
            firebaseAuth.signInWithCredential(credential).await()
        } catch (e: Exception) {
            _authError.value = e.message ?: "Firebase Google sign-in failed"
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun signOut() {
        _isLoading.value = true
        googleAuthClient.clearCredentialState()
        firebaseAuth.signOut()
        _isLoading.value = false
    }
}