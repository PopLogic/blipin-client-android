package com.poplogic.blipin.common.base.ratelimit

import kotlin.time.Duration

sealed interface RateLimitStatus {
    object Available : RateLimitStatus

    data class Locked(
        val theRemainingTimeMs: Duration,
    ) : RateLimitStatus
}
