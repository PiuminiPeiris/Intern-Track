package com.piumini.interntrack

import com.piumini.interntrack.utils.ProgressCalculator
import org.junit.Assert.assertEquals
import org.junit.Test

class ProgressCalculatorTest {
    @Test
    fun testCalculateProgress() {
        val result = ProgressCalculator.calculateProgress(
            completedDays = 2,
            totalDays = 5
        )
        assertEquals(0.4f, result, 0.001f)
    }

    @Test
    fun calculateProgress_returnsZeroWhenTotalDaysIsZero() {
        val result = ProgressCalculator.calculateProgress(
            completedDays = 3,
            totalDays = 0
        )
        assertEquals(0.0f, result, 0.001f)
    }

    @Test
    fun getProgressStatus_returnsCompleted() {
        val result = ProgressCalculator.getProgressStatus(1.0f)

        assertEquals("Completed", result)
    }

    @Test
    fun getProgressStatus_returnsInProgress() {
        val result = ProgressCalculator.getProgressStatus(0.5f)

        assertEquals("In Progress", result)
    }

    @Test
    fun getProgressStatus_returnsNotStarted() {
        val result = ProgressCalculator.getProgressStatus(0f)

        assertEquals("Not Started", result)
    }
}