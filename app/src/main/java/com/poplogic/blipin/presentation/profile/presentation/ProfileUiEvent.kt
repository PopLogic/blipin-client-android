package com.poplogic.blipin.presentation.profile.presentation

sealed interface ProfileUiEvent {
    data class ShowGeneralErrorDialog(
        val message: String,
        val retryAction: ProfileUiAction,
    ) : ProfileUiEvent
}
