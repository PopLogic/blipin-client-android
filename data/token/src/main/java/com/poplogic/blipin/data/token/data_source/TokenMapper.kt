package com.poplogic.blipin.data.token.data_source

import com.poplogic.blipin.api.token.models.RenewAccessTokenResponse
import com.poplogic.blipin.domain.auth.model.TokenEntity

class TokenMapper {
    fun mapRefreshTokenResponseToDomain(renewAccessTokenResponse: RenewAccessTokenResponse): TokenEntity =
        TokenEntity(
            accessToken = renewAccessTokenResponse.accessToken,
            refreshToken = renewAccessTokenResponse.refreshToken,
            isFirstTimeLogin = false,
        )
}
