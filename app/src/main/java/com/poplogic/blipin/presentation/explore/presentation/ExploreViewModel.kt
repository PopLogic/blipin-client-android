package com.poplogic.blipin.presentation.explore.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poplogic.blipin.domain.auth.model.UserAuthenticationState
import com.poplogic.blipin.domain.auth.usecase.UserAuthenticationStateUseCase
import com.poplogic.blipin.domain.connectivity.ConnectivityFlowBasedUseCase
import com.poplogic.blipin.domain.connectivity.model.ConnectivityState
import com.poplogic.blipin.domain.location.domain.Location
import com.poplogic.blipin.domain.location.location_updates.GetLocationUpdatesFlowBasedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ExploreViewModel(
    private val userAuthenticationStateUseCase: UserAuthenticationStateUseCase,
    private val connectivityUseCase: ConnectivityFlowBasedUseCase,
    private val locationUpdatesFlowBasedUseCase: GetLocationUpdatesFlowBasedUseCase,
) : ViewModel() {
    private val _connectionStatus: MutableStateFlow<ConnectivityState> =
        MutableStateFlow(ConnectivityState.UNKOWN)
    val connectionStatus = _connectionStatus

    private val userAuthenticationState: MutableStateFlow<UserAuthenticationState> =
        MutableStateFlow(
            UserAuthenticationState.Undetermined,
        )

    private val _uiState = MutableStateFlow<ExploreUiState>(ExploreUiState.Loading)
    val uiState: StateFlow<ExploreUiState> = _uiState

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
            connectivityUseCase(Unit).collect { connectivityState ->
                _connectionStatus.value = connectivityState
                if (connectivityState == ConnectivityState.CONNECTED) {
                    userAuthenticationStateUseCase(Unit).collect { userAuthentication ->
                        userAuthenticationState.value = userAuthentication
                    }
                }
            }
        }
    }
}
