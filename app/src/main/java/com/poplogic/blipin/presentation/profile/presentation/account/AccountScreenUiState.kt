package com.poplogic.blipin.presentation.profile.presentation.account

sealed interface AccountScreenUiState {
    data object Loading : AccountScreenUiState

    data object Error : AccountScreenUiState

    data class Success(
        val isLoading: Boolean,
    ) : AccountScreenUiState
}
