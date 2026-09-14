package com.poplogic.blipin.domain.auth.repository

import com.poplogic.blipin.domain.auth.IsNewUser
import com.poplogic.blipin.domain.auth.model.TokenEntity

interface AuthRepository {
    suspend fun signInWithGoogle(idToken: String): TokenEntity

    suspend fun signInWithApple(): TokenEntity

    suspend fun signInWithEmail(email: String): IsNewUser

    suspend fun verifyEmail(
        email: String,
        code: String,
    ): TokenEntity
}
