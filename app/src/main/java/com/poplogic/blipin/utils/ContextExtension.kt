package com.poplogic.blipin.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import com.poplogic.blipin.usecase.location.domain.LocationPermissionStatus

internal fun Context.locationPermissionStatus(): LocationPermissionStatus {
    val coarseLocationPermission = Manifest.permission.ACCESS_COARSE_LOCATION
    val fineLocationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    return when (PackageManager.PERMISSION_GRANTED) {
        checkSelfPermission(fineLocationPermission) -> {
            LocationPermissionStatus.FINE_LOCATION_GRANTED
        }

        checkSelfPermission(coarseLocationPermission) -> {
            LocationPermissionStatus.COARSE_LOCATION_GRANTED
        }

        else -> {
            LocationPermissionStatus.LOCATION_DENIED
        }
    }
}
