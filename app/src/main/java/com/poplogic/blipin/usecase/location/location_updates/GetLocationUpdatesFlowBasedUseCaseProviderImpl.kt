package com.poplogic.blipin.usecase.location.location_updates

import com.google.android.gms.location.FusedLocationProviderClient
import com.poplogic.blipin.usecase.location.domain.LocationServiceStatus
import com.poplogic.blipin.usecase.location.location_service_status.CanLocationServiceBeenUsedUseCase
import org.koin.core.annotation.Factory

@Factory(binds = [GetLocationUpdatesFlowBasedUseCaseProvider::class])
class GetLocationUpdatesFlowBasedUseCaseProviderImpl(
    private val canLocationServiceBeenUsedUseCase: CanLocationServiceBeenUsedUseCase,
    private val client: FusedLocationProviderClient,
) : GetLocationUpdatesFlowBasedUseCaseProvider {
    override fun provide(locationServiceStatus: LocationServiceStatus): GetLocationUpdatesFlowBasedUseCase =
        when {
            locationServiceStatus.canUseFineLocation -> {
                GetFineLocationUpdatesFlowBasedUseCaseImpl(
                    canLocationServiceBeenUsedUseCase,
                    client,
                )
            }

            locationServiceStatus.canUseCoarseLocation -> {
                GetCoarseLocationUpdatesFlowBasedUseCaseImpl(
                    canLocationServiceBeenUsedUseCase,
                    client,
                )
            }

            else -> {
                throw IllegalStateException("Location service cannot be used")
            }
        }
}
