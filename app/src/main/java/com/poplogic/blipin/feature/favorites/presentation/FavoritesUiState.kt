package com.poplogic.blipin.feature.favorites.presentation

sealed class FavoritesUiState {
    data object Loading : FavoritesUiState()

    data object Error : FavoritesUiState()

    data object SuccessAnonymous : FavoritesUiState()

    data class Success(
        val favorites: List<String>,
    ) : FavoritesUiState()
}
