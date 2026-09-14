import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

interface CryptoManager {
    fun encrypt(
        bytes: ByteArray,
        keyAlias: String,
    ): ByteArray

    fun decrypt(
        bytes: ByteArray,
        keyAlias: String,
    ): ByteArray
}

enum class CryptoKeyAlias(
    val alias: String,
) {
    TOKEN("token_encryption_key"),
}

class CryptoManagerImpl : CryptoManager {
    companion object {
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
    }

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

    private fun getSecretKey(keyAlias: String): SecretKey {
        val existingKey = keyStore.getEntry(keyAlias, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: generateKey(keyAlias)
    }

    private fun generateKey(keyAlias: String): SecretKey {
        val keyGenerator =
            KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        val spec =
            KeyGenParameterSpec
                .Builder(
                    keyAlias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
                ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setUserAuthenticationRequired(false) // 裝置解鎖即可，不需刷指紋
                .build()
        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }

    override fun encrypt(
        bytes: ByteArray,
        keyAlias: String,
    ): ByteArray {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(keyAlias))
        return cipher.iv + cipher.doFinal(bytes) // 拼接 IV 與密文
    }

    override fun decrypt(
        bytes: ByteArray,
        keyAlias: String,
    ): ByteArray {
        val iv = bytes.copyOfRange(0, 12)
        val encryptedBytes = bytes.copyOfRange(12, bytes.size)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(keyAlias), GCMParameterSpec(128, iv))
        return cipher.doFinal(encryptedBytes)
    }
}
