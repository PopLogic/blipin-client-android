package com.poplogic.blipin.presentation.authenticate.presentation.emailverification

sealed class EmailVerificationUiAction {
    data class OnOtpChanged(val otp: String) : EmailVerificationUiAction()

    data object OnVerifyButtonClicked : EmailVerificationUiAction()

    data object OnResendOtpButtonClicked : EmailVerificationUiAction()
}
