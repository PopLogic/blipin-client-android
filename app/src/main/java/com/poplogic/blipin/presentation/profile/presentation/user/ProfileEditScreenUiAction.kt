package com.poplogic.blipin.presentation.profile.presentation.user

sealed class ProfileEditScreenUiAction {
    data object OnDisplayNameEditButtonClick : ProfileEditScreenUiAction()

    data object OnBirthdateEditButtonClick : ProfileEditScreenUiAction()

    data object OnGenderEditButtonClick : ProfileEditScreenUiAction()

    data object OnProfilePictureEditButtonClick : ProfileEditScreenUiAction()
}
