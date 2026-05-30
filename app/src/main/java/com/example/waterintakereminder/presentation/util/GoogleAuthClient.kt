package com.example.waterintakereminder.presentation.util

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class GoogleAuthClient @Inject constructor(
    @ActivityContext private val context: Context
) {

    suspend fun getGoogleIdToken(): String = suspendCancellableCoroutine { continuation ->
        val credentialManager = CredentialManager.create(context)

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.default_web_client_id))
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        credentialManager.getCredential(
            context = context,
            request = request,
            cancellationSignal = null,
            executor = java.util.concurrent.Executors.newSingleThreadExecutor(),
            callback = object : androidx.credentials.CredentialManagerCallback<GetCredentialResponse> {
                override fun onResult(result: GetCredentialResponse) {
                    val credential = result.credential
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    continuation.resume(googleIdTokenCredential.idToken)
                }

                override fun onError(e: Exception) {
                    continuation.resumeWithException(e)
                }
            }
        )
    }
}