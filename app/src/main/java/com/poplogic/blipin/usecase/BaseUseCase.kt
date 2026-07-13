package com.poplogic.blipin.usecase

import kotlinx.coroutines.flow.Flow

interface FlowBasedUseCase<T, R> {
    operator fun invoke(param: T): Flow<R>
}

interface UseCase<T, R> {
    suspend operator fun invoke(param: T): R
}
