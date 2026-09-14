package com.poplogic.blipin.presentation.authenticate.presentation.updateprofile

sealed class UpdateUserProfileUiEffect {
    data object ProfileUpdateSuccess : UpdateUserProfileUiEffect()

    data class ProfileUpdateFailed(val errorMessage: String) : UpdateUserProfileUiEffect()
}
