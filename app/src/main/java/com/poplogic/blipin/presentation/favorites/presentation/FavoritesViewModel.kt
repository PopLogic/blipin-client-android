package com.poplogic.blipin.presentation.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poplogic.blipin.domain.auth.model.UserAuthenticationState
import com.poplogic.blipin.domain.auth.usecase.UserAuthenticationStateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class FavoritesViewModel(
    private val userAuthenticationStateUseCase: UserAuthenticationStateUseCase,
) : ViewModel() {
    private var _uiState: MutableStateFlow<FavoritesUiState> =
        MutableStateFlow(FavoritesUiState.Loading)
    val uiState: StateFlow<FavoritesUiState> = _uiState

    init {
        viewModelScope.launch {
            userAuthenticationStateUseCase(Unit).collect { state ->
                when (state) {
                    UserAuthenticationState.Authenticated -> {
                        _uiState.value =
                            FavoritesUiState.Success(
                                favorites = emptyList(), // TODO: Replace with actual favorites data when available
                            )
                    }

                    UserAuthenticationState.Unauthenticated -> {
                        _uiState.value = FavoritesUiState.SuccessAnonymous
                    }

                    UserAuthenticationState.Undetermined -> {
                        _uiState.value = FavoritesUiState.Loading
                    }
                }
            }
        }
    }
}
