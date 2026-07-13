package com.poplogic.blipin.usecase.location.location_updates

import com.poplogic.blipin.usecase.FlowBasedUseCase
import com.poplogic.blipin.usecase.location.domain.Location
import kotlinx.coroutines.flow.Flow

interface GetLocationUpdatesFlowBasedUseCase : FlowBasedUseCase<Unit, Location> {
    override fun invoke(param: Unit): Flow<Location>
}
