package com.poplogic.blipin.usecase.location.location_service_status

import android.content.Context
import android.location.LocationManager
import android.util.Log
import com.poplogic.blipin.usecase.location.domain.LocationServiceStatus
import com.poplogic.blipin.utils.locationPermissionStatus
import com.poplogic.blipin.utils.locationRequiredProvidersStatus
import org.koin.core.annotation.Factory

@Factory(binds = [CanLocationServiceBeenUsedUseCase::class])
class CanLocationServiceBeenUsedUseCaseImpl(
    private val context: Context,
) : CanLocationServiceBeenUsedUseCase {
    override suspend fun invoke(param: Unit): LocationServiceStatus {
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
        return locationServiceStatus
    }
}
