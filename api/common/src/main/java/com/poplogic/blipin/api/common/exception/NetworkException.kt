package com.poplogic.blipin.api.common.exception

import io.ktor.http.HttpStatusCode

sealed class NetworkException(
    val httpStatusCode: HttpStatusCode,
    message: String,
) : Exception(message) {
    class UnknownException(
        httpStatusCode: HttpStatusCode,
        message: String = "Unknown Error",
    ) : NetworkException(httpStatusCode, message)

    class ClientErrorException(
        httpStatusCode: HttpStatusCode,
        message: String = "Client Error",
    ) : NetworkException(httpStatusCode, message)

    class ServerErrorException(
        httpStatusCode: HttpStatusCode,
        message: String = "Server Error",
    ) : NetworkException(httpStatusCode, message)

    class UnauthorizedException(
        message: String = "Unauthorized",
    ) : NetworkException(HttpStatusCode.Unauthorized, message)

    class TooManyRequestsException(
        message: String = "Too Many Requests",
    ) : NetworkException(HttpStatusCode.TooManyRequests, message)
}
