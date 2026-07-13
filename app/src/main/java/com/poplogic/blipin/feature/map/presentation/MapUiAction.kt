package com.poplogic.blipin.feature.map.presentation

import com.poplogic.blipin.usecase.location.domain.LocationPermissionStatus

sealed class MapUiAction {
    data class LocationPermissionRequestResponded(
        val permissionStatus: LocationPermissionStatus,
    ) : MapUiAction()
}
