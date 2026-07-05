package com.poplogic.blipin.usecase

import kotlinx.coroutines.flow.Flow

interface UseCase<T, R> {
    operator fun invoke(param: T): Flow<R>
}
