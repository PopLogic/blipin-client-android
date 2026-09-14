package com.poplogic.blipin.presentation.profile.presentation.place

sealed interface PlaceScreenUiState {
    data object Loading : PlaceScreenUiState

    data object Error : PlaceScreenUiState

    data class Success(
        val isLoading: Boolean,
    ) : PlaceScreenUiState
}
