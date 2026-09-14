package com.poplogic.blipin.domain.location.location_service_status

import android.content.Context
import android.location.LocationManager
import android.util.Log
import com.poplogic.blipin.domain.location.domain.LocationServiceStatus
import com.poplogic.blipin.domain.location.util.locationPermissionStatus
import com.poplogic.blipin.domain.location.util.locationRequiredProvidersStatus

class CanLocationServiceBeenUsedUseCaseImpl(
    private val context: Context,
) : CanLocationServiceBeenUsedUseCase {
    override suspend fun invoke(param: Unit): Result<LocationServiceStatus> {
        val locationPermissionStatus = context.locationPermissionStatus()
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationProviderStatus = locationManager.locationRequiredProvidersStatus()
        val locationServiceStatus =
            LocationServiceStatus.fromPermissionAndProvidersStatus(
                locationPermissionStatus,
                locationProviderStatus,
            )
        Log.d(
            "CanLocationServiceBeenUsedUseCaseImpl",
            "Location service status: $locationServiceStatus",
        )
        return Result.success(locationServiceStatus)
    }
}
