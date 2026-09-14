package com.poplogic.blipin.presentation.authenticate.presentation.emailverification

import kotlin.time.Duration

data class EmailVerificationUiState(
    val email: String = "",
    val otp: String = "",
    val isLoading: Boolean = false,
    val remainingTime: Duration = Duration.ZERO,
    val canResendOtp: Boolean = false,
) {
    companion object {
        fun initialState(email: String): EmailVerificationUiState =
            EmailVerificationUiState(
                email = email,
            )
    }
}
