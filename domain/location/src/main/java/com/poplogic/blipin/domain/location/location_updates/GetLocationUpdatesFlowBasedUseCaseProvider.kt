package com.poplogic.blipin.domain.location.location_updates

import com.poplogic.blipin.domain.location.domain.LocationServiceStatus

interface GetLocationUpdatesFlowBasedUseCaseProvider {
    fun provide(locationServiceStatus: LocationServiceStatus): GetLocationUpdatesFlowBasedUseCase
}
