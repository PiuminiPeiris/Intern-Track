package com.piumini.interntrack.utils

import java.security.MessageDigest

object AuthUtils {
    private const val VALID_USERNAME = "intern"

    private const val STORED_PASSWORD_HASH =
        "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4"

    fun hashPassword(password: String): String {
        val byte = MessageDigest
            .getInstance("SHA-256")
            .digest(password.toByteArray())

        return byte.joinToString("") {
            "%02x".format(it)
        }
    }

    fun isValidLogin(
        username: String,
        password: String
    ): Boolean {
        val enteredPasswordHash = hashPassword(password)

        return username == VALID_USERNAME &&
            hashPassword(password) == STORED_PASSWORD_HASH
    }
}