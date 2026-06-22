package com.piumini.interntrack.utils

import com.piumini.interntrack.data.TrainingTopic

object ProgressCalculator {
    fun calculateProgress(
        completedDays: Int,
        totalDays: Int
    ): Float {
        if (totalDays <= 0) {
            return 0.0f
        }
        return completedDays.toFloat() / totalDays.toFloat()
    }

    fun getProgressStatus(progress: Float): String {
        return when {
            progress >= 1.0f -> "Completed"
            progress >= 0.8f -> "Almost There"
            progress >= 0.5f -> "In Progress"
            else -> "Not Started"
        }
    }
}