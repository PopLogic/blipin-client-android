package com.poplogic.blipin.data.token.data_source.local

import CryptoKeyAlias
import CryptoManager
import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Serializable
data class TokenPrefs(
    val refreshToken: String? = null,
) {
    fun toRefreshToken(): String? = refreshToken
}

class TokenPrefsSerializer(
    private val cryptoManager: CryptoManager,
) : Serializer<TokenPrefs> {
    override val defaultValue: TokenPrefs
        get() =
            TokenPrefs(
                refreshToken = null,
            )

    override suspend fun readFrom(input: InputStream): TokenPrefs {
        val encryptedBytes = input.readBytes()
        if (encryptedBytes.isEmpty()) return defaultValue

        return try {
            val decryptedBytes = cryptoManager.decrypt(encryptedBytes, CryptoKeyAlias.TOKEN.alias)
            Json.decodeFromString(TokenPrefs.serializer(), decryptedBytes.decodeToString())
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue // 解密失敗時（如金鑰損毀）返回預設空物件，避免 App 崩潰
        }
    }

    override suspend fun writeTo(
        t: TokenPrefs,
        output: OutputStream,
    ) {
        val jsonString = Json.encodeToString(TokenPrefs.serializer(), t)
        val bytes = jsonString.encodeToByteArray()
        // 2. 加密並寫入檔案
        val encryptedBytes = cryptoManager.encrypt(bytes, CryptoKeyAlias.TOKEN.alias)
        withContext(Dispatchers.IO) {
            output.write(encryptedBytes)
        }
    }
}
