package com.poplogic.blipin.domain.auth.usecase

import com.poplogic.blipin.domain.auth.AuthManager

class LogOutUseCaseImpl(
    private val authManager: AuthManager,
) : LogOutUseCase {
    override suspend fun invoke(param: Unit): Result<Unit> {
        try {
            authManager.logout()
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
