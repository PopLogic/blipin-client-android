package com.poplogic.blipin.presentation.authenticate.presentation

import androidx.credentials.Credential

sealed class AuthenticationUiAction {
    data class OnCredentialReturned(
        val credential: Credential,
    ) : AuthenticationUiAction()

    data object OnCredentialStarted : AuthenticationUiAction()

    data object OnCredentialFailed : AuthenticationUiAction()

    data class OnEmailTextFieldChanged(
        val email: String,
    ) : AuthenticationUiAction()

    data class OnEmailSignInButtonClicked(
        val email: String,
    ) : AuthenticationUiAction()

    data object OnEmailTextFieldCleared : AuthenticationUiAction()
}
