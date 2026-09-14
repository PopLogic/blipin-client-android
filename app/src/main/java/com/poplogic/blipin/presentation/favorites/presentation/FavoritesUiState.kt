package com.poplogic.blipin.presentation.favorites.presentation

sealed class FavoritesUiState {
    data object Loading : FavoritesUiState()

    data object Error : FavoritesUiState()

    data object SuccessAnonymous : FavoritesUiState()

    data class Success(
        val favorites: List<String>,
    ) : FavoritesUiState()
}
