package com.poplogic.blipin.domain.auth.usecase

import com.poplogic.blipin.common.base.ratelimit.RateLimitStatus
import com.poplogic.blipin.domain.common.FlowBasedUseCase
import kotlinx.coroutines.flow.Flow

interface EmailVerificationResendRateLimitStatusUseCase : FlowBasedUseCase<String, RateLimitStatus> {
    override operator fun invoke(param: String): Flow<RateLimitStatus>
}
