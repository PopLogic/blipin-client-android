package com.poplogic.blipin.feature.authenticate.presentation

data class AuthenticationUiState(
    val isLoading: Boolean,
    val isEmailRegistered: Boolean,
    val errorMessage: String?,
) {
    companion object {
        val Initial = AuthenticationUiState(
            isLoading = false,
            isEmailRegistered = false,
            errorMessage = "",
        )
    }
}


