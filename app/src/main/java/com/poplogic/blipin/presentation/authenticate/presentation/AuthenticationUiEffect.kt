package com.poplogic.blipin.presentation.authenticate.presentation

sealed class AuthenticationUiEffect {
    data class ShowCredentialSignUpBottomSheet(
        val displayName: String?,
    ) : AuthenticationUiEffect()

    data class ShowEmailSignUpBottomSheet(
        val email: String,
    ) : AuthenticationUiEffect()

    data class ShowEmailSignInBottomSheet(
        val email: String,
    ) : AuthenticationUiEffect()

    data class NavigateToHomeScreen(
        val isPasswordBeenUpdated: Boolean,
    ) : AuthenticationUiEffect()

    data object ShowEmailVerificationSheet : AuthenticationUiEffect()

    data class ShowEmailCheckError(
        val errorMessage: String,
    ) : AuthenticationUiEffect()
}
