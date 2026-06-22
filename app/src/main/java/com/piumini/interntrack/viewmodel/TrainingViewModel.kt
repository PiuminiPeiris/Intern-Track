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

@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val repository: TrainingRepository
) : ViewModel() {

    val posts = mutableStateListOf<PostResponse>()
    val paginationStatus = mutableStateOf("Pagination not started")
    val currentPage = mutableStateOf(1)
    val isLoadingPosts = mutableStateOf(false)
    val networkStatus = mutableStateOf("Real network request not started")

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

    fun loadNextPostPage() {
        if (isLoadingPosts.value) return

        isLoadingPosts.value = true
        paginationStatus.value = "Loading page ${currentPage.value}..."

        viewModelScope.launch {
            try {
                val newPosts = repository.fetchPostByPage(
                    page = currentPage.value,
                    limit = 10
                )
                posts.addAll(newPosts)
                posts.addAll(newPosts)

                paginationStatus.value =
                    "Page ${currentPage.value} loaded. Total posts: ${posts.size}"
                currentPage.value = currentPage.value + 1
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
}