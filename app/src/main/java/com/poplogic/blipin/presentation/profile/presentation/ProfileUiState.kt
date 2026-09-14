package com.poplogic.blipin.presentation.profile.presentation

import com.poplogic.blipin.domain.user.model.UserEntity

sealed class ProfileUiState {
    data object Loading : ProfileUiState()

    data object Error : ProfileUiState()

    data class SuccessAnonymous(
        val isLoading: Boolean,
    ) : ProfileUiState()

    data class Success(
        val userEntity: UserEntity,
        val isLoading: Boolean,
    ) : ProfileUiState()
}
