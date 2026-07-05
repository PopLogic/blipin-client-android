package com.poplogic.blipin.usecase

import kotlinx.coroutines.flow.Flow

interface UseCase<T, R> {
    fun invoke(param: T): Flow<R>
}
