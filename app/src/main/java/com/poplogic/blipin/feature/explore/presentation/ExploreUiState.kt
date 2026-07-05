package com.poplogic.blipin.feature.explore.presentation

sealed class ExploreUiState {
    data object Loading : ExploreUiState()

    data object Error : ExploreUiState()

    data class Success(
        val branding: List<String>,
        val newShop: List<String>,
        val hot: List<String>,
        val explore: List<String>,
    ) : ExploreUiState()
}
