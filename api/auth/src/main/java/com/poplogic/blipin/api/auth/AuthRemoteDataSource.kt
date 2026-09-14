package com.poplogic.blipin.api.auth

import com.poplogic.blipin.api.auth.apis.AuthApi
import com.poplogic.blipin.api.auth.models.EmailLoginRequest
import com.poplogic.blipin.api.auth.models.EmailLoginResponse
import com.poplogic.blipin.api.auth.models.GoogleLoginRequest
import com.poplogic.blipin.api.auth.models.GoogleLoginResponse
import com.poplogic.blipin.api.auth.models.VerifyEmailRequest
import com.poplogic.blipin.api.auth.models.VerifyEmailResponse
import com.poplogic.blipin.api.common.exception.NetworkExceptionHandler
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

class AuthRemoteDataSource(
    private val networkExceptionHandler: NetworkExceptionHandler = NetworkExceptionHandler(),
    private val authApi: AuthApi,
) {
    suspend fun googleLogin(idToken: String): GoogleLoginResponse {
        val httpResponse =
            authApi.authGooglePost(
                GoogleLoginRequest(idToken),
            )
        if (!httpResponse.success) {
            throw networkExceptionHandler.handleException(
                httpStatusCode = HttpStatusCode.fromValue(httpResponse.status),
                message = httpResponse.response.toString(),
            )
        }
        return httpResponse.response.body()
    }

    suspend fun appleLogin() {
        authApi.authApplePost()
    }

    suspend fun emailLogin(email: String): EmailLoginResponse {
        val httpResponse =
            authApi.authEmailLoginPost(
                EmailLoginRequest(email),
            )
        if (!httpResponse.success) {
            throw networkExceptionHandler.handleException(
                httpStatusCode = HttpStatusCode.fromValue(httpResponse.status),
                message = httpResponse.response.toString(),
            )
        }
        return httpResponse.response.body()
    }

    suspend fun emailVerification(verifyEmailRequest: VerifyEmailRequest): VerifyEmailResponse {
        val httpResponse =
            authApi.authEmailVerifyPost(
                verifyEmailRequest,
            )
        if (!httpResponse.success) {
            throw networkExceptionHandler.handleException(
                httpStatusCode = HttpStatusCode.fromValue(httpResponse.status),
                message = httpResponse.response.toString(),
            )
        }
        return httpResponse.response.body()
    }
}
