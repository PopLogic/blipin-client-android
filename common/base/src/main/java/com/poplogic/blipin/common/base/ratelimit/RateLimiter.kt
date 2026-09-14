package com.poplogic.blipin.common.base.ratelimit

import kotlinx.coroutines.flow.Flow

interface RateLimiter {
    val statusFlow: Flow<RateLimitStatus>

    fun currentStatus(): RateLimitStatus

    fun tryTrigger(): Boolean
}
