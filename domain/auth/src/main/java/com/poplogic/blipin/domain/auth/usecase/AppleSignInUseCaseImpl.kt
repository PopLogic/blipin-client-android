package com.poplogic.blipin.domain.auth.usecase

import com.poplogic.blipin.domain.auth.AuthManager

class AppleSignInUseCaseImpl(
    private val authManager: AuthManager,
) : AppleSignInUseCase {
    override suspend fun invoke(param: String): Result<Unit> {
        try {
            authManager.signInWithApple(param)
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
