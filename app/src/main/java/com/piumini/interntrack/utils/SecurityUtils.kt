package com.piumini.interntrack.utils

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.io.encoding.Base64

object SecurityUtils {
    private const val SECRET_KEY = "1234567890123456"

    fun encrypt(text: String): String {
        val keySpec = SecretKeySpec(
            SECRET_KEY.toByteArray(Charsets.UTF_8),
            "AES"
        )
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)

        val encryptedBytes = cipher.doFinal(
            text.toByteArray(Charsets.UTF_8)
        )
        return bytesToHex(encryptedBytes)
    }

    private fun bytesToHex(bytes: ByteArray): String {
        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }

    fun decrypt(encryptedText: String): String {
        val keySpec = SecretKeySpec(
            SECRET_KEY.toByteArray(),
            "AES"
        )
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, keySpec)

        val decodedBytes = hexToBytes(encryptedText)
        val decryptedBytes = cipher.doFinal(decodedBytes)

        return String(decryptedBytes, Charsets.UTF_8)
    }

    private fun hexToBytes(hex: String): ByteArray {
        return hex.chunked(2)
            .map {
                it.toInt(16).toByte()
            }
            .toByteArray()
    }
}