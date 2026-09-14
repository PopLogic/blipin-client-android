package com.poplogic.blipin.presentation.authenticate.presentation.updateprofile

import com.poplogic.blipin.domain.user.model.UserEntity
import kotlin.time.Instant

data class UpdateUserProfileUiState(
    val nickname: String,
    val gender: UserEntity.Gender? = null,
    val birthday: Instant? = null,
    val isLoading: Boolean = false,
    val isNicknameValid: Boolean = false,
) {
    val canSubmit: Boolean
        get() = nickname.isNotEmpty() && gender != null && birthday != null && !isLoading
}
