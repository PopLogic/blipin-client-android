package com.poplogic.blipin.usecase.location.location_service_status

import com.poplogic.blipin.usecase.UseCase
import com.poplogic.blipin.usecase.location.domain.LocationServiceStatus

interface CanLocationServiceBeenUsedUseCase : UseCase<Unit, LocationServiceStatus> {
    override suspend fun invoke(param: Unit): LocationServiceStatus
}
