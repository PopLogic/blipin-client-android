package com.poplogic.blipin.usecase.location.domain

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION

enum class LocationPermissionStatus {
    COARSE_LOCATION_GRANTED,
    FINE_LOCATION_GRANTED,
    LOCATION_DENIED,
    ;

    companion object {
        fun fromPermissionsMap(permissionsMap: Map<String, Boolean>): LocationPermissionStatus {
            val coarseLocationGranted = permissionsMap[ACCESS_COARSE_LOCATION] ?: false
            val fineLocationGranted = permissionsMap[ACCESS_FINE_LOCATION] ?: false

            return when {
                fineLocationGranted -> FINE_LOCATION_GRANTED
                coarseLocationGranted -> COARSE_LOCATION_GRANTED
                else -> LOCATION_DENIED
            }
        }
    }
}
