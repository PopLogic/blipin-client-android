package com.poplogic.blipin.usecase.location.location_updates

import com.poplogic.blipin.usecase.location.domain.LocationServiceStatus

interface GetLocationUpdatesFlowBasedUseCaseProvider {
    fun provide(locationServiceStatus: LocationServiceStatus): GetLocationUpdatesFlowBasedUseCase
}
