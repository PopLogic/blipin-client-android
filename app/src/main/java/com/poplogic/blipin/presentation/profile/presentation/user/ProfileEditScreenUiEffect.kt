package com.poplogic.blipin.presentation.profile.presentation.user

import com.poplogic.blipin.domain.user.model.UserEntity
import kotlinx.datetime.LocalDate

sealed class ProfileEditScreenUiEffect {
    data object ShowGeneralErrorDialog : ProfileEditScreenUiEffect()

    data class ShowDisplayNameEditBottomSheet(
        val currentDisplayName: String,
    ) : ProfileEditScreenUiEffect()

    data class ShowBirthdateEditBottomSheet(
        val currentBirthdate: LocalDate?,
    ) : ProfileEditScreenUiEffect()

    data class ShowGenderEditBottomSheet(
        val currentGender: UserEntity.Gender?,
    ) : ProfileEditScreenUiEffect()

    data object ShowEditSuccessSnackbar : ProfileEditScreenUiEffect()
}
