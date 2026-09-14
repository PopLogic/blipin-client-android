package com.poplogic.blipin.presentation.profile.presentation.user

import com.poplogic.blipin.domain.user.model.UserEntity

sealed class ProfileEditScreenUiState {
    data object Loading : ProfileEditScreenUiState()

    data object Error : ProfileEditScreenUiState()

    data class Success(
        val userEntity: UserEntity,
    ) : ProfileEditScreenUiState()
}
