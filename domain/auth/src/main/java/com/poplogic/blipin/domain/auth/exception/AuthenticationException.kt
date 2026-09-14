package com.poplogic.blipin.domain.auth.exception

sealed class AuthenticationException : Exception() {
    class NotAuthenticatedException : AuthenticationException()

    class RefreshTokenFailedException : AuthenticationException()

    class SessionInvalidatedException : AuthenticationException()

    class RateLimitExceededException : AuthenticationException()
}
