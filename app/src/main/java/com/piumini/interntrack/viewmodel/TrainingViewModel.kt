package com.piumini.interntrack.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piumini.interntrack.data.TrainingRepository
import com.piumini.interntrack.data.TrainingTopic
import com.piumini.interntrack.network.PostResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.piumini.interntrack.network.FastAndroidNetworkingService

@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val repository: TrainingRepository
) : ViewModel() {

    val posts = mutableStateListOf<PostResponse>()
    val paginationStatus = mutableStateOf("Pagination not started")
    val currentPage = mutableStateOf(1)
    val isLoadingPosts = mutableStateOf(false)
    val hasMorePosts = mutableStateOf(true)
    val networkStatus = mutableStateOf("Real network request not started")
    val fastNetworkingStatus = mutableStateOf("Fast Android Networking not started")

    fun getTopics(): List<TrainingTopic> {
        return repository.getTrainingTopics()
    }

    fun getRepositoryStatus(): String {
        return repository.getRepositoryStatus()
    }

    fun getTopicCount(): Int {
        return repository.getTopicCount()
    }

    fun fetchRealNetworkUpdate() {
        networkStatus.value = "Loading data from GitHub API..."

        viewModelScope.launch {
            try {
                val result = repository.fetchRealNetworkUpdate()
                networkStatus.value = result

                Log.d("InternTrackNetwork", result)

            } catch (e: Exception) {
                networkStatus.value = "Network error: ${e.message}"

                Log.e("InternTrackNetwork", "Network error", e)
            }
        }
    }

    fun loadNextPostsPage() {
        if (isLoadingPosts.value || !hasMorePosts.value) return

        isLoadingPosts.value = true
        paginationStatus.value = "Loading page ${currentPage.value}..."

        viewModelScope.launch {
            try {
                val newPosts = repository.fetchPostByPage(
                    page = currentPage.value,
                    limit = 10
                )

                if (newPosts.isEmpty()) {
                    hasMorePosts.value = false
                    paginationStatus.value = "No more posts to load"
                } else {
                    posts.addAll(newPosts)

                    paginationStatus.value =
                        "Page ${currentPage.value} loaded. Total posts: ${posts.size}"

                    currentPage.value = currentPage.value + 1
                }
            } catch (e: Exception) {
                paginationStatus.value = "Error loading posts: ${e.message}"
            } finally {
                isLoadingPosts.value = false
            }
        }
    }

    fun logFirebaseTrainingEvent() {
        repository.logFirebaseTrainingEvent()
    }

    fun fetchFirebaseRemoteConfig() {
        repository.fetchFirebaseRemoteConfig()
    }

    fun testFastAndroidNetworking() {
        fastNetworkingStatus.value = "Fast Android Networking loading..."

        FastAndroidNetworkingService.fetchSamplePost(
            onSuccess = { result ->
                fastNetworkingStatus.value = result
            },
            onError = { error ->
                fastNetworkingStatus.value = error
            }
        )
    }
}