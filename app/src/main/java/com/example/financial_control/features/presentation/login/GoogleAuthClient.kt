package com.example.financial_control.features.presentation.login

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class GoogleAuthClient(private val context: Context)  {

    private val credentialManager = CredentialManager.create(context)
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun isUserSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    suspend fun signIn(): Boolean {
        if (isUserSignedIn()) {
            return true
        }

        try {
            val result = buildCredentialRequest()
            return handleSigIn(result)

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            println("Error: ${e.message}")
            return false
        }
    }

    private suspend fun handleSigIn(result: GetCredentialResponse): Boolean {
        val credential = result.credential

        if (
            credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            try {
                val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                println("name: ${tokenCredential.displayName}")
                println("email: ${tokenCredential.id}")
                println("image: ${tokenCredential.profilePictureUri}")

                val authCredential = GoogleAuthProvider.getCredential(tokenCredential.idToken, null)
                val authResult = firebaseAuth.signInWithCredential(authCredential).await()

                return authResult.user != null
            } catch (e: Exception) {
                println("credential: ${e.message}")
                return false
            }
        } else {
            println("Invalid credential type")
            return false
        }
    }

    private suspend fun buildCredentialRequest(): GetCredentialResponse {
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId("539364929696-csjnh8cbuv639gu2f637kf1cl1bf55kt.apps.googleusercontent.com")
                    .setAutoSelectEnabled(false)
                    .build()
            )
            .build()

        return credentialManager.getCredential(context, request)
    }

    suspend fun signOut() {
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
        firebaseAuth.signOut()
    }
}