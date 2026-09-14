package com.poplogic.blipin.domain.location.location_updates

import com.poplogic.blipin.domain.common.FlowBasedUseCase
import com.poplogic.blipin.domain.location.domain.Location
import kotlinx.coroutines.flow.Flow

interface GetLocationUpdatesFlowBasedUseCase : FlowBasedUseCase<Unit, Location> {
    override fun invoke(param: Unit): Flow<Location>
}
