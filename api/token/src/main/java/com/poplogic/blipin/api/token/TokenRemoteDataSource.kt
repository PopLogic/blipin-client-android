package com.poplogic.blipin.api.token

import com.poplogic.blipin.api.common.exception.NetworkExceptionHandler
import com.poplogic.blipin.api.token.apis.TokenApi
import com.poplogic.blipin.api.token.models.RenewAccessTokenRequest
import com.poplogic.blipin.api.token.models.RenewAccessTokenResponse
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

class TokenRemoteDataSource(
    private val networkExceptionHandler: NetworkExceptionHandler,
    private val tokenApi: TokenApi,
) {
    suspend fun refreshToken(renewAccessTokenRequest: RenewAccessTokenRequest): RenewAccessTokenResponse {
        val httpResponse =
            tokenApi.tokenRenewAccessTokenPost(
                renewAccessTokenRequest,
            )

        if (!httpResponse.success) {
            throw networkExceptionHandler.handleException(
                httpStatusCode = HttpStatusCode.fromValue(httpResponse.status),
                message = httpResponse.response.toString(),
            )
        }
        return httpResponse.response.body()
    }

    suspend fun revokeToken() {
        val httpResponse = tokenApi.tokenRevokePost()

        if (!httpResponse.success) {
            throw networkExceptionHandler.handleException(
                httpStatusCode = HttpStatusCode.fromValue(httpResponse.status),
                message = httpResponse.response.toString(),
            )
        }
    }
}
