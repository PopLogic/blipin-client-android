package com.poplogic.blipin.data.token.data_source.local

import android.content.Context
import com.poplogic.blipin.data.token.tokenDataStore
import kotlinx.coroutines.flow.firstOrNull

class LocalTokenDataSource(
    private val context: Context,
) {
    suspend fun saveRefreshToken(refreshToken: String) {
        context.tokenDataStore.updateData { currentTokens ->
            currentTokens.copy(
                refreshToken = refreshToken,
            )
        }
    }

    suspend fun getRefreshToken(): String? {
        val tokenPrefs = context.tokenDataStore.data.firstOrNull()
        return tokenPrefs?.refreshToken
    }
}
