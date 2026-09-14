package com.poplogic.blipin.domain.auth.repository

import com.poplogic.blipin.domain.auth.model.TokenEntity

interface TokenRepository {
    suspend fun saveRefreshToken(refreshToken: String)

    suspend fun getRefreshToken(): String?

    suspend fun refreshTokens(refreshToken: String): Result<TokenEntity>

    suspend fun revokeRefreshToken(): Result<Unit>
}
