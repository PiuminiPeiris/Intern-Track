package com.piumini.interntrack.ui.components

fun getRepositoryStatus(): String {
    return "TrainingRepository injected successfully using Hilt"
}

fun getTopicCount(): Int {
    return getTrainingTopics().size
}