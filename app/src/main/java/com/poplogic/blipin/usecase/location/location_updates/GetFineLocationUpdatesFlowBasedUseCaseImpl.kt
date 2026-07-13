package com.poplogic.blipin.usecase.location.location_updates

import android.Manifest
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.poplogic.blipin.usecase.location.domain.Location
import com.poplogic.blipin.usecase.location.location_service_status.CanLocationServiceBeenUsedUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory(binds = [GetLocationUpdatesFlowBasedUseCase::class])
class GetFineLocationUpdatesFlowBasedUseCaseImpl(
    private val canLocationServiceBeenUsedUseCase: CanLocationServiceBeenUsedUseCase,
    private val client: FusedLocationProviderClient,
) : GetLocationUpdatesFlowBasedUseCase {
    @RequiresPermission(
        allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION],
    )
    override fun invoke(param: Unit): Flow<Location> =
        callbackFlow {
            if (!(canLocationServiceBeenUsedUseCase.invoke(Unit).canUseFineLocation)) {
                close(IllegalStateException("Location service cannot be used"))
                return@callbackFlow
            }
            val locationCallback =
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        super.onLocationResult(locationResult)

                        locationResult.locations.lastOrNull()?.let { location ->
                            trySend(Location(location.latitude, location.longitude))
                        }
                    }
                }

            val locationRequest =
                LocationRequest
                    .Builder(Priority.PRIORITY_HIGH_ACCURACY, 10_000)
                    .setWaitForAccurateLocation(false)
                    .setMinUpdateIntervalMillis(5_000)
                    .setMaxUpdateDelayMillis(10_000)
                    .build()

            withContext(Dispatchers.Main) {
                client.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        trySend(Location(it.latitude, it.longitude))
                    }
                }
                client.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper(),
                )
            }

            awaitClose {
                client.removeLocationUpdates(locationCallback)
            }
        }
}
