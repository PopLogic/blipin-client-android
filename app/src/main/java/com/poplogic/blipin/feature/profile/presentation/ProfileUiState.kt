package com.poplogic.blipin.feature.profile.presentation

sealed class ProfileUiState {
    data object Loading : ProfileUiState()

    data object Error : ProfileUiState()

    data class Success(
        val userName: String,
        val userEmail: String,
        val userProfilePictureUrl: String,
        val birthdayString: String,
    ) : ProfileUiState()
}
