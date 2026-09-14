package com.poplogic.blipin.presentation.profile.presentation.notification

sealed interface NotificationScreenUiState {
    data object Loading : NotificationScreenUiState

    data object Error : NotificationScreenUiState

    data class Success(
        val isLoading: Boolean,
    ) : NotificationScreenUiState
}
