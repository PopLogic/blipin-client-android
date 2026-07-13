package com.poplogic.blipin.usecase.authentication

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory(binds = [CheckIfEmailRegisteredFlowBasedUseCase::class])
class CheckIfEmailRegisteredFlowBasedUseCaseImpl : CheckIfEmailRegisteredFlowBasedUseCase {
    override fun invoke(param: String): Flow<Boolean> =
        flowOf(false).map {
            delay(1000) // Simulate network delay
            it
        }
}
