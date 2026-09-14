package com.poplogic.blipin.presentation.authenticate.presentation.composables.sections

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.poplogic.blipin.R
import com.poplogic.blipin.common.base.environment.WEB_CLIENT_ID
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.presentation.authenticate.presentation.AuthenticationUiAction
import com.poplogic.blipin.presentation.authenticate.presentation.AuthenticationViewModel
import kotlinx.coroutines.launch

@Composable
internal fun CredentialButtonsSection(
    viewModel: AuthenticationViewModel,
    credentialManager: CredentialManager,
    context: Context,
) {
    val coroutineScope = rememberCoroutineScope()
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(44.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        IconButton(
            modifier =
                Modifier
                    .wrapContentHeight()
                    .aspectRatio(1f)
                    .background(color = Color.Transparent),
            onClick = {
                viewModel.onAction(AuthenticationUiAction.OnCredentialStarted)
                val signInOption =
                    GetGoogleIdOption
                        .Builder()
                        .setServerClientId(WEB_CLIENT_ID)
                        .setFilterByAuthorizedAccounts(false)
                        .setAutoSelectEnabled(false)
                        .build()

                val request: GetCredentialRequest =
                    GetCredentialRequest
                        .Builder()
                        .addCredentialOption(signInOption)
                        .build()
                coroutineScope.launch {
                    try {
                        val result =
                            credentialManager.getCredential(
                                request = request,
                                context = context,
                            )
                        Log.d("AuthenticationScreen", "Google Sign-In result: $result")
                        viewModel.onAction(
                            AuthenticationUiAction.OnCredentialReturned(
                                credential = result.credential,
                            ),
                        )
                    } catch (e: GetCredentialCancellationException) {
                        // Handle cancellation
                        Log.d(
                            "AuthenticationScreen",
                            "Google Sign-In cancelled: ${e.message}",
                        )
                        viewModel.onAction(AuthenticationUiAction.OnCredentialFailed)
                    } catch (e: NoCredentialException) {
                        // Handle other exceptions
                        Log.e(
                            "AuthenticationScreen",
                            "Google Sign-In error: ${e.message}",
                            e,
                        )
                        handleNoCredentialException(context as Activity, viewModel)
                    } catch (e: Exception) {
                        // Handle other exceptions
                        Log.e(
                            "AuthenticationScreen",
                            "Google Sign-In error: ${e.message}",
                            e,
                        )
                        viewModel.onAction(AuthenticationUiAction.OnCredentialFailed)
                    }
                }
            },
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_google),
                contentDescription = "Google login".hardcoded(),
                tint = Color.Unspecified,
            )
        }
        Spacer(Modifier.width(16.dp))
        IconButton(
            modifier =
                Modifier
                    .wrapContentHeight()
                    .aspectRatio(1f)
                    .background(color = Color.Transparent),
            onClick = { /* Handle Apple login click */ },
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_apple),
                contentDescription = "Apple login".hardcoded(),
                tint = Color.Unspecified,
            )
        }
    }
}

private suspend fun handleNoCredentialException(
    activity: Activity,
    viewModel: AuthenticationViewModel,
) {
    try {
        val signInWithGoogleOption =
            GetSignInWithGoogleOption
                .Builder(serverClientId = WEB_CLIENT_ID)
                .build()
        val request =
            GetCredentialRequest
                .Builder()
                .addCredentialOption(signInWithGoogleOption)
                .build()
        val result =
            CredentialManager.create(activity).getCredential(
                context = activity,
                request = request,
            )
        viewModel.onAction(
            AuthenticationUiAction.OnCredentialReturned(
                credential = result.credential,
            ),
        )
    } catch (e: Exception) {
        Log.e(
            "AuthenticationScreen",
            "handleNoCredentialException: ",
            e,
        )
        viewModel.onAction(AuthenticationUiAction.OnCredentialFailed)
    }
}
