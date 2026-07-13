package com.poplogic.blipin.feature.explore.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poplogic.blipin.usecase.connectivity.ConnectivityFlowBasedUseCase
import com.poplogic.blipin.usecase.connectivity.domain.ConnectivityState
import com.poplogic.blipin.usecase.location.domain.Location
import com.poplogic.blipin.usecase.location.location_updates.GetLocationUpdatesFlowBasedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ExploreViewModel(
    private val connectivityUseCase: ConnectivityFlowBasedUseCase,
    private val locationUpdatesFlowBasedUseCase: GetLocationUpdatesFlowBasedUseCase,
) : ViewModel() {
    private val _connectionStatus: StateFlow<ConnectivityState> =
        MutableStateFlow(ConnectivityState.CONNECTED)
    val connectionStatus: StateFlow<ConnectivityState> = _connectionStatus

    private val _locationStatus: StateFlow<Location?> = MutableStateFlow(null)
    val locationStatus: StateFlow<Location?> = _locationStatus

    private val _locationPermissionStatus: StateFlow<Boolean> = MutableStateFlow(false)
    val locationPermissionStatus: StateFlow<Boolean> = _locationPermissionStatus

    private val _storeStatus: StateFlow<ExploreUiState> = MutableStateFlow(ExploreUiState.Loading)
    val storeStatus: StateFlow<ExploreUiState> = _storeStatus

    private val _signInStatus: StateFlow<Boolean> = MutableStateFlow(false)
    val signInStatus: StateFlow<Boolean> = _signInStatus

    init {

        viewModelScope.launch {
            connectivityUseCase(Unit).collect { isConnected ->
                (_connectionStatus as MutableStateFlow).value = isConnected
            }
        }
    }
}
