package com.piumini.interntrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.piumini.interntrack.data.TrainingTopic
import com.piumini.interntrack.network.PostResponse
import com.piumini.interntrack.ui.components.TopicCard
import com.piumini.interntrack.viewmodel.TrainingViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    trainingViewModel: TrainingViewModel = hiltViewModel()
) {
    HomeScreenContent(
        getTopics = { trainingViewModel.getTopics() },
        fetchRealNetworkUpdate = { trainingViewModel.fetchRealNetworkUpdate() },
        logFirebaseTrainingEvent = { trainingViewModel.logFirebaseTrainingEvent() },
        fetchFirebaseRemoteConfig = { trainingViewModel.fetchFirebaseRemoteConfig() },
        loadNextPostsPage = { trainingViewModel.loadNextPostsPage() },
        posts = trainingViewModel.posts,
        fastNetworkingStatus = trainingViewModel.fastNetworkingStatus.value,
        testFastAndroidNetworking = {
            trainingViewModel.testFastAndroidNetworking()
        }
    )
}

@Composable
fun HomeScreenContent(
    getTopics: () -> List<TrainingTopic>,
    fetchRealNetworkUpdate: () -> Unit = {},
    logFirebaseTrainingEvent: () -> Unit = {},
    fetchFirebaseRemoteConfig: () -> Unit = {},
    loadNextPostsPage: () -> Unit = {},
    posts: List<PostResponse> = emptyList(),
    fastNetworkingStatus: String = "Fast Android Networking not started",
    testFastAndroidNetworking: () -> Unit = {}
) {
    val visibleTopicCount = remember { mutableStateOf(3) }
    val isLoadingMoreTopics = remember { mutableStateOf(false) }
    val selectedWeek = remember { mutableStateOf("Week 3") }
    val selectedTopic = remember { mutableStateOf<TrainingTopic?>(null) }
    val weeks: List<String> = listOf(
        "Week 1",
        "Week 2",
        "Week 3",
        "Week 4"
    )
    val topics = remember {
        mutableStateListOf<TrainingTopic>().apply {
            addAll(getTopics())
        }
    }
    val currentTopic = selectedTopic.value
    val listState = rememberLazyListState()

    val isSyncing = remember { mutableStateOf(false) }
    val syncStatus = remember { mutableStateOf("Ready") }
    val scope = rememberCoroutineScope()
    val showReportScreen = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        fetchRealNetworkUpdate()
        logFirebaseTrainingEvent()
        fetchFirebaseRemoteConfig()

        if (posts.isEmpty()) {
            loadNextPostsPage()
        }
    }

    if (showReportScreen.value) {
        ReportScreen(
            onBack = {
                showReportScreen.value = false
            },
            fastNetworkingStatus = fastNetworkingStatus
        )
    } else if (currentTopic != null) {
        TopicDetailScreen(
            topic = currentTopic,
            onBack = {
                selectedTopic.value = null
            },
            onMarkComplete = {
                val index = topics.indexOfFirst {
                    it.day == currentTopic.day
                }

                if (index != -1) {
                    val updatedTopic = currentTopic.copy(
                        progress = 1.0f,
                        status = "Completed"
                    )

                    topics[index] = updatedTopic
                    selectedTopic.value = updatedTopic
                }
            }
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 56.dp,
                    bottom = 24.dp
                ),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF7F0093),
                        contentColor = Color.Black
                    ),
                ) {
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 24.dp,
                                end = 24.dp,
                                top = 18.dp,
                                bottom = 24.dp
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 24.dp,
                                    end = 24.dp,
                                    top = 18.dp,
                                    bottom = 24.dp
                                ),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    if (!isSyncing.value) {
                                        isSyncing.value = true
                                        syncStatus.value = "Syncing..."

                                        scope.launch {
                                            try {
                                                delay(3000)
                                                syncStatus.value = "Synced"
                                            } catch (e: Exception) {
                                                syncStatus.value = "Sync failed"
                                            } finally {
                                                isSyncing.value = false
                                            }
                                        }
                                    }
                                },
                                enabled = !isSyncing.value,
                            ) {
                                if (isSyncing.value) {
                                    CircularProgressIndicator(
                                        strokeWidth = 2.dp,
                                        modifier = Modifier.size(18.dp)
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Sync,
                                        contentDescription = "Sync",
                                        modifier = Modifier.size(18.dp),
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                        Text(
                            text = "Intern Track",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = "Training Progress App • ${syncStatus.value}",
                            fontSize = 14.sp,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(34.dp))

                        Button(
                            onClick = {
                                testFastAndroidNetworking()
                                showReportScreen.value = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF9C00B3),
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Generate Report",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    weeks.forEach { week ->
                        item {
                            Button(
                                onClick = {
                                    selectedWeek.value = week
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selectedWeek.value == week) {
                                        Color(0xFF7F0093)
                                    } else {
                                        Color(0xFF430149)
                                    },
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = week)
                            }
                        }
                    }
                }
            }


            item {
                Text(
                    text = "${selectedWeek.value} Topics",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
            }

            topics.take(visibleTopicCount.value).forEach { topic ->
                item {
                    TopicCard(
                        topic = topic,
                        onClick = {
                            selectedTopic.value = topic
                        }
                    )
                }
            }

            if (visibleTopicCount.value < topics.size) {
                item {
                    Button(
                        onClick = {
                            isLoadingMoreTopics.value = true

                            scope.launch {
                                delay(2000)
                                visibleTopicCount.value =
                                    (visibleTopicCount.value + 3).coerceAtMost(topics.size)
                                isLoadingMoreTopics.value = false
                            }
                        },
                        enabled = !isLoadingMoreTopics.value,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF7F0093),
                            contentColor = Color.White
                        )
                    ) {
                        if (isLoadingMoreTopics.value) {
                            CircularProgressIndicator(
                                color = Color.White,
                            )
                        } else {
                            Text(
                                text = "Load More Topics",
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreenContent(
            getTopics = {
                listOf(
                    TrainingTopic("Day 1", "Kotlin Basics", "Introduction to Kotlin syntax", 1.0f, "Completed"),
                    TrainingTopic("Day 2", "Jetpack Compose", "Building UI with Compose", 0.5f, "In Progress"),
                    TrainingTopic("Day 3", "State Management", "Using remember and mutableStateOf", 0.0f, "Not Started"),
                    TrainingTopic("Day 4", "Navigation", "Navigating between screens", 0.0f, "Not Started")
                )
            }
        )
    }
}
