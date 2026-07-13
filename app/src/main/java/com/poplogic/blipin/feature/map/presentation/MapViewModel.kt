package com.poplogic.blipin.feature.map.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poplogic.blipin.usecase.location.domain.Location
import com.poplogic.blipin.usecase.location.domain.LocationServiceStatus
import com.poplogic.blipin.usecase.location.location_service_status.CanLocationServiceBeenUsedUseCase
import com.poplogic.blipin.usecase.location.location_updates.GetLocationUpdatesFlowBasedUseCase
import com.poplogic.blipin.usecase.location.location_updates.GetLocationUpdatesFlowBasedUseCaseProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MapViewModel(
    private val getLocationUpdatesFlowBasedUseCaseProvider: GetLocationUpdatesFlowBasedUseCaseProvider,
    private val canLocationServiceBeenUsedUseCase: CanLocationServiceBeenUsedUseCase,
) : ViewModel() {
    private val _locationState: StateFlow<Location?> = MutableStateFlow(null)
    val locationState: StateFlow<Location?> = _locationState

    private val _locationServiceStatus: StateFlow<LocationServiceStatus> =
        MutableStateFlow(
            LocationServiceStatus.NotDetermined,
        )
    val locationServiceStatus: StateFlow<LocationServiceStatus> = _locationServiceStatus

    init {
        viewModelScope.launch {
            flowOf(canLocationServiceBeenUsedUseCase.invoke(Unit)).collect { locationServiceStatus ->
                (_locationServiceStatus as MutableStateFlow).value = locationServiceStatus
                if (locationServiceStatus.canUseLocation) {
                    triggerLocationUpdates(locationServiceStatus)
                }
            }
        }
    }

    private lateinit var locationUpdateUseCase: GetLocationUpdatesFlowBasedUseCase

    private suspend fun triggerLocationUpdates(locationServiceStatus: LocationServiceStatus) {
        locationUpdateUseCase =
            getLocationUpdatesFlowBasedUseCaseProvider
                .provide(locationServiceStatus)

        locationUpdateUseCase(Unit).collect { location ->
            Log.d("MapViewModel", "Location update: $location")
            (_locationState as MutableStateFlow).value = location
        }
    }

    fun onUiAction(action: MapUiAction) {
        when (action) {
            is MapUiAction.LocationPermissionRequestResponded -> {
                viewModelScope.launch {
                    val locationServiceStatus = canLocationServiceBeenUsedUseCase.invoke(Unit)
                    if (locationServiceStatus.canUseLocation) {
                        triggerLocationUpdates(locationServiceStatus)
                    }
                    (_locationServiceStatus as MutableStateFlow).value = locationServiceStatus
                }
            }
        }
    }
}
