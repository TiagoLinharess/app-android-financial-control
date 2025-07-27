package com.example.financial_control.domain.repository

import com.example.financial_control.domain.client.GoogleAuthClient
import com.example.financial_control.domain.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

interface AuthRepositoryInterface {
    val currentUser: StateFlow<UserModel?>
    suspend fun signIn()
    suspend fun signOut()
}

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val googleAuthClient: GoogleAuthClient
) : AuthRepositoryInterface {

    private val _currentUser = MutableStateFlow<UserModel?>(null)
    override val currentUser: StateFlow<UserModel?> = _currentUser

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

    override suspend fun signIn() {
        val credential = googleAuthClient.getCredentialResponse()
        firebaseAuth.signInWithCredential(credential).await()
    }

    override suspend fun signOut() {
        googleAuthClient.clearCredentialState()
        firebaseAuth.signOut()
    }
}