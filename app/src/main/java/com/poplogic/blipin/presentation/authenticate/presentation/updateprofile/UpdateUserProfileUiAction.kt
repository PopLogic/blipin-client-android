package com.poplogic.blipin.presentation.authenticate.presentation.updateprofile

import com.poplogic.blipin.domain.user.model.UserEntity
import kotlin.time.Instant

sealed class UpdateUserProfileUiAction {
    data class OnNicknameChanged(
        val nickname: String,
    ) : UpdateUserProfileUiAction()

    data class OnGenderChanged(
        val gender: UserEntity.Gender,
    ) : UpdateUserProfileUiAction()

    data class OnBirthdayChanged(
        val birthday: Instant,
    ) : UpdateUserProfileUiAction()

    data object OnSubmitButtonClicked : UpdateUserProfileUiAction()
}
