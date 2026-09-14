package com.poplogic.blipin.presentation.profile.presentation.developer

sealed interface DeveloperScreenUiState {
    data object Loading : DeveloperScreenUiState

    data object Error : DeveloperScreenUiState

    data class Success(
        val isLoading: Boolean,
    ) : DeveloperScreenUiState
}
