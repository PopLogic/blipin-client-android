package com.poplogic.blipin.feature.authenticate.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class AuthenticationViewModel : ViewModel() {
    private val _uiState: StateFlow<AuthenticationUiState> = MutableStateFlow(AuthenticationUiState.Initial)
    val uiState: StateFlow<AuthenticationUiState> = _uiState

    init {

    }
}
