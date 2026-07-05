package com.poplogic.blipin.feature.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class FavoritesViewModel : ViewModel() {
    private val _uiState: StateFlow<FavoritesUiState> =
        flowOf(
            FavoritesUiState.SuccessAnonymous,
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FavoritesUiState.Loading,
        )
    val uiState: StateFlow<FavoritesUiState> = _uiState
}
