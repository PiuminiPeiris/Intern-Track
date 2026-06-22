package com.piumini.interntrack

import com.piumini.interntrack.utils.SecurityUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class SecurityUtilsTest {
    @Test
    fun encrypt_shouldReturnDifferentText() {
        val originalText = "InternTrack Security Test"

        val encryptedText = SecurityUtils.encrypt(originalText)

        assertNotEquals(originalText, encryptedText)
    }

    @Test
    fun decrypt_shouldReturnOriginalText() {
        val originalText = "InternTrack Security Test"

        val encryptedText = SecurityUtils.encrypt(originalText)
        val decryptedText = SecurityUtils.decrypt(encryptedText)

        assertEquals(originalText, decryptedText)
    }
}