package com.poplogic.blipin.data.auth

import com.poplogic.blipin.api.auth.AuthRemoteDataSource
import com.poplogic.blipin.api.auth.models.VerifyEmailRequest
import com.poplogic.blipin.domain.auth.IsNewUser
import com.poplogic.blipin.domain.auth.model.TokenEntity
import com.poplogic.blipin.domain.auth.repository.AuthRepository

class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
) : AuthRepository {
    override suspend fun signInWithGoogle(idToken: String): TokenEntity {
        val googleLoginResponse = authRemoteDataSource.googleLogin(idToken)
        return TokenEntity(
            accessToken = googleLoginResponse.accessToken,
            refreshToken = googleLoginResponse.refreshToken,
            isFirstTimeLogin = googleLoginResponse.isFirstTimeLogin,
        )
    }

    override suspend fun signInWithApple(): TokenEntity =
        TokenEntity(
            accessToken = "",
            refreshToken = "",
            isFirstTimeLogin = false,
        )

    override suspend fun signInWithEmail(email: String): IsNewUser {
        val emailLoginResponse = authRemoteDataSource.emailLogin(email)
        return emailLoginResponse.isNewUser
    }

    override suspend fun verifyEmail(
        email: String,
        code: String,
    ): TokenEntity {
        val emailVerifyResponse =
            authRemoteDataSource.emailVerification(VerifyEmailRequest(email, code))
        return TokenEntity(
            accessToken = emailVerifyResponse.accessToken,
            refreshToken = emailVerifyResponse.refreshToken,
            isFirstTimeLogin = true, // emailVerifyResponse.isFirstTimeLogin,
        )
    }
}
