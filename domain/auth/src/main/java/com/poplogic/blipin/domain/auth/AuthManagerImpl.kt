package com.poplogic.blipin.domain.auth

import RateLimiterImpl
import android.util.Log
import com.poplogic.blipin.common.base.ratelimit.RateLimitStatus
import com.poplogic.blipin.common.base.ratelimit.RateLimiter
import com.poplogic.blipin.domain.auth.exception.AuthenticationException
import com.poplogic.blipin.domain.auth.model.TokenEntity
import com.poplogic.blipin.domain.auth.model.UserAuthenticationState
import com.poplogic.blipin.domain.auth.repository.AuthRepository
import com.poplogic.blipin.domain.auth.repository.TokenRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.concurrent.Volatile

class AuthManagerImpl(
    private val tokenRepository: TokenRepository,
    private val authRepository: AuthRepository,
) : AuthManager {
    companion object {
        @Volatile
        private var sessionGeneration: Long = 0
    }

    private val refreshMutex = Mutex()
    private var _accessToken: String? = null
    private val _userAuthenticationStateFlow =
        MutableSharedFlow<UserAuthenticationState>(replay = 1)
    override val userAuthenticationStateFlow = _userAuthenticationStateFlow

    override fun emailVerificationResendRateLimitStatus(email: String): Flow<RateLimitStatus> {
        val rateLimiter =
            emailRateLimiterMap.getOrPut(email) {
                RateLimiterImpl()
            }
        return rateLimiter.statusFlow
    }

    private val emailRateLimiterMap = mutableMapOf<String, RateLimiter>()
    private val emailUsedMap = mutableMapOf<String, Boolean>()

    init {
        _userAuthenticationStateFlow.tryEmit(UserAuthenticationState.Undetermined)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                refresh()
                _userAuthenticationStateFlow.emit(UserAuthenticationState.Authenticated)
            } catch (e: AuthenticationException) {
                _userAuthenticationStateFlow.emit(UserAuthenticationState.Unauthenticated)
            }
        }
    }

    override val accessToken: String?
        get() = _accessToken

    override suspend fun getTokenEntity(): TokenEntity? {
        val refreshToken = tokenRepository.getRefreshToken()
        return if (refreshToken != null && _accessToken != null) {
            TokenEntity(
                accessToken = _accessToken!!,
                refreshToken = refreshToken,
                isFirstTimeLogin = false,
            )
        } else {
            null
        }
    }

    override suspend fun logout() {
        sessionGeneration++

        _accessToken = null
        tokenRepository.revokeRefreshToken()
        _userAuthenticationStateFlow.emit(UserAuthenticationState.Unauthenticated)
    }

    override suspend fun signInWithGoogle(idToken: String): IsNewUser {
        try {
            val tokenEntity = authRepository.signInWithGoogle(idToken)
            saveTokenEntity(tokenEntity)
            _userAuthenticationStateFlow.emit(UserAuthenticationState.Authenticated)
            return tokenEntity.isFirstTimeLogin
        } catch (e: Exception) {
            Log.e("AuthManagerImpl", "Error signing in with Google: ${e.message}")
            _userAuthenticationStateFlow.emit(UserAuthenticationState.Unauthenticated)
            throw e
        }
    }

    override suspend fun signInWithApple(idToken: String): IsNewUser {
        try {
//            val tokenEntity = authRepository.signInWithApple(idToken)
//            saveTokenEntityAndUpdateFlow(tokenEntity)
//            _userAuthenticationStateFlow.emit(UserAuthenticationState.Authenticated)
            return true
        } catch (e: Exception) {
            Log.e("AuthManagerImpl", "Error signing in with Apple: ${e.message}")
            _userAuthenticationStateFlow.emit(UserAuthenticationState.Unauthenticated)
            throw e
        }
    }

    override suspend fun signInWithEmail(email: String): IsNewUser {
        try {
            val rateLimiter =
                emailRateLimiterMap.getOrPut(email) {
                    RateLimiterImpl()
                }

            if (rateLimiter.currentStatus() == RateLimitStatus.Available) {
                val result = authRepository.signInWithEmail(email)
                rateLimiter.tryTrigger()
                emailUsedMap[email] = result
                return result
            }
            return emailUsedMap[email].also {
                if (it == null) {
                    Log.e("AuthManagerImpl", "Email used status not found for email: $email")
                }
            } ?: false
        } catch (e: Exception) {
            Log.e("AuthManagerImpl", "Error signing in with Email: ${e.message}")
            _userAuthenticationStateFlow.emit(UserAuthenticationState.Unauthenticated)
            throw e
        }
    }

    override suspend fun verifyEmail(
        email: String,
        code: String,
    ) {
        try {
            val tokenEntity = authRepository.verifyEmail(email, code)
            saveTokenEntity(tokenEntity)
            _userAuthenticationStateFlow.emit(UserAuthenticationState.Authenticated)
        } catch (e: Exception) {
            Log.e("AuthManagerImpl", "Error verifying email: ${e.message}")
            _userAuthenticationStateFlow.emit(UserAuthenticationState.Unauthenticated)
            throw e
        }
    }

    override suspend fun refresh(): TokenEntity {
        val originAccessToken = accessToken
        val generation = sessionGeneration
        return refreshMutex.withLock {
            // Another request may have refreshed
            // while we were waiting.
            val currentToken = _accessToken

            if (currentToken != null &&
                currentToken != originAccessToken
            ) {
                // The token has already been refreshed by another coroutine.
                return@withLock TokenEntity(
                    accessToken = currentToken,
                    refreshToken = tokenRepository.getRefreshToken()!!,
                    isFirstTimeLogin = false,
                )
            }

            if (generation != sessionGeneration) {
                throw AuthenticationException.SessionInvalidatedException()
            }

            val refreshToken =
                tokenRepository.getRefreshToken()
                    ?: throw AuthenticationException.NotAuthenticatedException()

            val refreshTokenResult = tokenRepository.refreshTokens(refreshToken)
            val tokens =
                refreshTokenResult.getOrElse {
                    throw AuthenticationException.RefreshTokenFailedException()
                }
            _accessToken = tokens.accessToken

            tokenRepository.saveRefreshToken(
                tokens.refreshToken,
            )

            return@withLock tokens
        }
    }

    private suspend fun saveTokenEntity(tokenEntity: TokenEntity) {
        _accessToken = tokenEntity.accessToken
        tokenRepository.saveRefreshToken(tokenEntity.refreshToken)
    }
}
