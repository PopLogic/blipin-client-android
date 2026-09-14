package com.poplogic.blipin.domain.auth.usecase

import com.poplogic.blipin.common.base.ratelimit.RateLimitStatus
import com.poplogic.blipin.domain.auth.AuthManager
import kotlinx.coroutines.flow.Flow

class EmailVerificationResendRateLimitStatusUseCaseImpl(
    private val authManager: AuthManager,
) : EmailVerificationResendRateLimitStatusUseCase {
    override operator fun invoke(param: String): Flow<RateLimitStatus> = authManager.emailVerificationResendRateLimitStatus(param)
}
