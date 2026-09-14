package com.poplogic.blipin.domain.common

import kotlinx.coroutines.flow.Flow

interface FlowBasedUseCase<T, R> {
    operator fun invoke(param: T): Flow<R>
}

interface UseCase<T, R> {
    suspend operator fun invoke(param: T): Result<R>
}
