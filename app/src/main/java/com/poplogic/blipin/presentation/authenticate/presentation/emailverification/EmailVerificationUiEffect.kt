package com.poplogic.blipin.presentation.authenticate.presentation.emailverification

sealed class EmailVerificationUiEffect {
    data object VerificationSuccess : EmailVerificationUiEffect()

    data class VerificationFailed(val errorMessage: String) : EmailVerificationUiEffect()

    data object OtpResent : EmailVerificationUiEffect()
}
