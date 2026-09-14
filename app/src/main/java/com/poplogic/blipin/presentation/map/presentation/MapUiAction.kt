package com.poplogic.blipin.presentation.map.presentation

import com.poplogic.blipin.domain.location.domain.LocationPermissionStatus

sealed class MapUiAction {
    data class LocationPermissionRequestResponded(
        val permissionStatus: LocationPermissionStatus,
    ) : MapUiAction()
}
