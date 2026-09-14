package com.poplogic.blipin.domain.location.location_service_status

import com.poplogic.blipin.domain.common.UseCase
import com.poplogic.blipin.domain.location.domain.LocationServiceStatus

interface CanLocationServiceBeenUsedUseCase : UseCase<Unit, LocationServiceStatus> {
    override suspend fun invoke(param: Unit): Result<LocationServiceStatus>
}
