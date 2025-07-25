package com.example.financial_control.domain.client

import android.content.Context
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GoogleAuthClient @Inject constructor(
    @ApplicationContext private val context: Context
)  {
    private val credentialManager = CredentialManager.create(context)

    // TODO: Implementar em um env
    private val clientId = "539364929696-csjnh8cbuv639gu2f637kf1cl1bf55kt.apps.googleusercontent.com"

    private suspend fun buildCredentialResponse(): GetCredentialResponse {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(clientId)
            .setAutoSelectEnabled(false)
            .build()
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return credentialManager.getCredential(context, request)
    }

    private fun getGoogleIdTokenFromResult(response: GetCredentialResponse): String? {
        return when (val credential = response.credential) {
            is GoogleIdTokenCredential -> credential.idToken
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        GoogleIdTokenCredential.createFrom(credential.data).idToken
                    } catch (e: Exception) {
                        Log.e("GoogleAuthClient", "Error parsing CustomCredential for Google ID Token", e)
                        null
                    }
                } else {
                    null
                }
            }
            else -> null
        }
    }

    suspend fun getCredentialResponse(): AuthCredential {
        val credentialResponse = buildCredentialResponse()
        val tokenId = getGoogleIdTokenFromResult(credentialResponse)
        return GoogleAuthProvider.getCredential(tokenId, null)
    }

    suspend fun clearCredentialState() {
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
    }
}