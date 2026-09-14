package com.poplogic.blipin.domain.auth.usecase

import com.poplogic.blipin.domain.auth.AuthManager

class EmailVerificationUseCaseImpl(
    private val authManager: AuthManager,
) : EmailVerificationUseCase {
    override suspend fun invoke(param: EmailVerificationParam): Result<Unit> {
        try {
            authManager.verifyEmail(email = param.email, code = param.verificationCode)
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
