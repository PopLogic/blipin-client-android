package com.poplogic.blipin.domain.auth

import com.poplogic.blipin.common.base.ratelimit.RateLimitStatus
import com.poplogic.blipin.domain.auth.model.TokenEntity
import com.poplogic.blipin.domain.auth.model.UserAuthenticationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

typealias IsNewUser = Boolean

interface AuthManager {
    val accessToken: String?
    val userAuthenticationStateFlow: MutableSharedFlow<UserAuthenticationState>

    fun emailVerificationResendRateLimitStatus(email: String): Flow<RateLimitStatus>

    suspend fun getTokenEntity(): TokenEntity?

    suspend fun logout()

    suspend fun signInWithGoogle(idToken: String): IsNewUser

    suspend fun signInWithApple(idToken: String): IsNewUser

    suspend fun signInWithEmail(email: String): IsNewUser

    suspend fun verifyEmail(
        email: String,
        code: String,
    )

    suspend fun refresh(): TokenEntity
}
