package com.poplogic.blipin.feature.explore.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poplogic.blipin.usecase.connectivity.ConnectivityState
import com.poplogic.blipin.usecase.connectivity.ConnectivityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val connectivityUseCase: ConnectivityUseCase,
) : ViewModel() {
    private val _connectionStatus: StateFlow<ConnectivityState> =
        MutableStateFlow(ConnectivityState.CONNECTED)
    val connectionStatus: StateFlow<ConnectivityState> = _connectionStatus

    init {
        viewModelScope.launch {
            connectivityUseCase(Unit).collect { isConnected ->
                (_connectionStatus as MutableStateFlow).value = isConnected
            }
        }
    }
}
