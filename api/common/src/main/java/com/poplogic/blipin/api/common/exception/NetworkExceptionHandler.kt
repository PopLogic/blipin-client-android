package com.poplogic.blipin.api.common.exception

import io.ktor.http.HttpStatusCode

class NetworkExceptionHandler {
    fun handleException(
        httpStatusCode: HttpStatusCode,
        message: String,
    ): NetworkException =
        when (httpStatusCode.value) {
            401 -> {
                NetworkException.UnauthorizedException(message)
            }

            429 -> {
                NetworkException.TooManyRequestsException(message)
            }

            in 400..499 -> {
                NetworkException.ClientErrorException(httpStatusCode, message)
            }

            in 500..599 -> {
                NetworkException.ServerErrorException(httpStatusCode, message)
            }

            else -> {
                NetworkException.UnknownException(
                    httpStatusCode,
                    message,
                )
            }
        }
}
