package com.example.common.utils

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object AES128Util {
    private const val SECRET_KEY = "EXAMPLE"

    fun encrypt(message: String): String {
        return Base64.getEncoder().encodeToString(encryptToBytes(message))
    }

    fun decrypt(digest: String): String {
        val bytesToDecrypt = Base64.getDecoder().decode(digest)
        return String(decryptToBytes(bytesToDecrypt))
    }

    private fun encryptToBytes(digest: String): ByteArray {
        val secretKey = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
        val cipher: Cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return cipher.doFinal(digest.toByteArray(charset("UTF-8")))
    }

    private fun decryptToBytes(digest: ByteArray): ByteArray {
        val secretKey = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        return cipher.doFinal(digest)
    }
}
