package com.poplogic.blipin.data.token

import android.content.Context
import androidx.datastore.dataStore
import com.poplogic.blipin.api.token.TokenRemoteDataSource
import com.poplogic.blipin.api.token.models.RenewAccessTokenRequest
import com.poplogic.blipin.data.token.data_source.TokenMapper
import com.poplogic.blipin.data.token.data_source.local.LocalTokenDataSource
import com.poplogic.blipin.data.token.data_source.local.TokenPrefs
import com.poplogic.blipin.data.token.data_source.local.TokenPrefsSerializer
import com.poplogic.blipin.domain.auth.model.TokenEntity
import com.poplogic.blipin.domain.auth.repository.TokenRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private object DataStoreDependencies : KoinComponent {
    val tokenPrefsSerializer: TokenPrefsSerializer by inject()
}

val Context.tokenDataStore by dataStore(
    fileName = "token_prefs.json", // 雖然是加密二進位檔，但內容本質是加密後的 JSON
    serializer = DataStoreDependencies.tokenPrefsSerializer,
)

class TokenRepositoryImpl(
    private val context: Context,
    private val localTokenDataSource: LocalTokenDataSource,
    private val tokenRemoteDataSource: TokenRemoteDataSource,
    private val tokenMapper: TokenMapper,
) : TokenRepository {
    override suspend fun saveRefreshToken(refreshToken: String) {
        localTokenDataSource.saveRefreshToken(refreshToken)
    }

    // get the refresh token from the local data source
    override suspend fun getRefreshToken(): String? = localTokenDataSource.getRefreshToken()

    // refresh token by calling the remote API and return the new tokens
    override suspend fun refreshTokens(refreshToken: String): Result<TokenEntity> {
        try {
            val renewAccessTokenResponse =
                tokenRemoteDataSource.refreshToken(RenewAccessTokenRequest(refreshToken = refreshToken))
            return Result.success(
                tokenMapper.mapRefreshTokenResponseToDomain(
                    renewAccessTokenResponse,
                ),
            )
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun revokeRefreshToken(): Result<Unit> {
        try {
            tokenRemoteDataSource.revokeToken()
            clearRefreshToken() // 清除本地儲存的 refresh token
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    private suspend fun clearRefreshToken() {
        context.tokenDataStore.updateData { TokenPrefs() } // 重置為預設值
    }
}
