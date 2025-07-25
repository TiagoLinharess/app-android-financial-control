package com.example.financial_control.domain.repository

import com.example.financial_control.domain.client.GoogleAuthClient
import com.example.financial_control.domain.models.UserModel
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

    private val _currentUser = MutableStateFlow<UserModel?>(null)
    val currentUser: StateFlow<UserModel?> = _currentUser

    init {
        firebaseAuth.addAuthStateListener { auth ->
            val firebaseUser = auth.currentUser
            if (firebaseUser != null) {
                _currentUser.value = UserModel(
                    id = firebaseUser.uid,
                    name = firebaseUser.displayName,
                    email = firebaseUser.email,
                    photo = firebaseUser.photoUrl
                )
            } else {
                _currentUser.value = null
            }
        }
    }

    suspend fun signIn() {
        val credential = googleAuthClient.getCredentialResponse()
        firebaseAuth.signInWithCredential(credential).await()
    }

    suspend fun signOut() {
        googleAuthClient.clearCredentialState()
        firebaseAuth.signOut()
    }
}