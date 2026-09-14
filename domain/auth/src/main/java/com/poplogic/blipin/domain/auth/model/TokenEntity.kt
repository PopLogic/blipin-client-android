package com.poplogic.blipin.domain.auth.model

data class TokenEntity(
    val accessToken: String,
    val refreshToken: String,
    val isFirstTimeLogin: Boolean,
)
