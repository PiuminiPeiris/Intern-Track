package com.piumini.interntrack.data

import com.piumini.interntrack.FirebaseService
import com.piumini.interntrack.network.PostResponse
import com.piumini.interntrack.network.PostRetrofitClient
import com.piumini.interntrack.network.RetrofitClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrainingRepository @Inject constructor(
    private val firebaseService: FirebaseService
) {
    fun getTrainingTopics(): List<TrainingTopic> {
        return listOf(
            TrainingTopic(
                day = "Day 1",
                title = "Compose Advanced",
                description = "LazyColumn, LazyRow, Cards and Detail Screen",
                progress = 0.75f,
                status = "In Progress"
            ),
            TrainingTopic(
                day = "Day 2",
                title = "Android Threads",
                description = "Main thread, UI thread and worker thread",
                progress = 1.0f,
                status = "Completed"
            ),
            TrainingTopic(
                day = "Day 3",
                title = "Coroutines",
                description = "Multithreading using Kotlin coroutines",
                progress = 1.0f,
                status = "Completed"
            ),
            TrainingTopic(
                day = "Day 4",
                title = "Dependency Injection",
                description = "Explore Dagger Hilt in Android",
                progress = 0.8f,
                status = "In Progress"
            ),
            TrainingTopic(
                day = "Day 5",
                title = "Networking",
                description = "Connect Android app with backend",
                progress = 0.2f,
                status = "Not Started"
            )
        )
    }

    fun getRepositoryStatus(): String {
        return "TrainingRepository injected successfully using Hilt"
    }

    fun getTopicCount(): Int {
        return getTrainingTopics().size
    }

    suspend fun fetchRealNetworkUpdate(): String {
        val repo = RetrofitClient.apiService.getRetrofitRepository()

        return """
            Real API data loaded successfully
            Repository: ${repo.fullName}
            Description: ${repo.description ?: "No description"}
            Stars: ${repo.stars}
            Language: ${repo.language ?: "Unknown"}
        """.trimIndent()
    }

    suspend fun fetchPostByPage (
        page: Int,
        limit: Int
    ): List<PostResponse> {
        return PostRetrofitClient.apiService.getPosts(
            page, limit
        )
    }

    fun logFirebaseTrainingEvent() {
        firebaseService.logTrainingHomeOpened()
    }

    fun fetchFirebaseRemoteConfig() {
        firebaseService.fetchRemoteConfig()
    }
}