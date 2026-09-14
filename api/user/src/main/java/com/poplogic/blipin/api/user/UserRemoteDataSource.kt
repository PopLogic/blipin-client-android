package com.poplogic.blipin.api.user

import com.poplogic.blipin.api.common.exception.NetworkExceptionHandler
import com.poplogic.blipin.api.user.apis.UserApi
import com.poplogic.blipin.api.user.models.GetUserProfileResponse
import com.poplogic.blipin.api.user.models.UpdateUserProfileRequest
import com.poplogic.blipin.api.user.models.UpdateUserProfileResponse
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

class UserRemoteDataSource(
    private val networkExceptionHandler: NetworkExceptionHandler,
    private val userApi: UserApi,
) {
    suspend fun getUser(): GetUserProfileResponse {
        val httpResponse = userApi.userGet()
        if (!httpResponse.success) {
            throw (
                networkExceptionHandler.handleException(
                    HttpStatusCode.fromValue(httpResponse.status),
                    httpResponse.response.toString(),
                )
            )
        }
        return httpResponse.response.body()
    }

    suspend fun updateUser(updateUserRequest: UpdateUserProfileRequest): UpdateUserProfileResponse {
        val httpResponse = userApi.userPatch(updateUserRequest)
        if (!httpResponse.success) {
            throw (
                networkExceptionHandler.handleException(
                    HttpStatusCode.fromValue(httpResponse.status),
                    httpResponse.response.toString(),
                )
            )
        }
        return httpResponse.response.body()
    }
}
