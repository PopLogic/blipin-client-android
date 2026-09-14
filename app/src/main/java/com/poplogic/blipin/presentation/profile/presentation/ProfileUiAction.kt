package com.poplogic.blipin.presentation.profile.presentation

sealed class ProfileUiAction {
    data object OnLogOutButtonClick : ProfileUiAction()
}
