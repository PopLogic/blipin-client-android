package com.poplogic.blipin.presentation.profile.presentation.support

sealed interface SupportScreenUiState {
    data object Loading : SupportScreenUiState

    data object Error : SupportScreenUiState

    data class Success(
        val isLoading: Boolean,
    ) : SupportScreenUiState
}
