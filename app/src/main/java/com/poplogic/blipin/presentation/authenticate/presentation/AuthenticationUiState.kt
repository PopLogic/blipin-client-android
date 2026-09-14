package com.poplogic.blipin.presentation.authenticate.presentation

data class AuthenticationUiState(
    val isLoading: Boolean = false,
    val email: String = "",
    val credentialDisplayName: String? = null,
) {
    val isEmailEmpty: Boolean
        get() = email.isEmpty()

    val isEmailValid: Boolean
        get() =
            android.util.Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()

    val isEmailStateError: Boolean
        get() = !isEmailEmpty && !isEmailValid
}
