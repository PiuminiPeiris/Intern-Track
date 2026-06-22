package com.piumini.interntrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.piumini.interntrack.data.TrainingTopic
import com.piumini.interntrack.ui.components.TopicCard
import androidx.compose.runtime.mutableStateListOf
import com.piumini.interntrack.ui.components.ThreadReportCard
import com.piumini.interntrack.ui.components.CoroutineReportCard
import androidx.hilt.navigation.compose.hiltViewModel
import com.piumini.interntrack.ui.components.PaginationSampleCard
import com.piumini.interntrack.viewmodel.TrainingViewModel

@Composable
fun HomeScreen(
    trainingViewModel: TrainingViewModel = hiltViewModel()
) {
    val selectedWeek = remember { mutableStateOf("Week 3") }

    val selectedTopic = remember { mutableStateOf<TrainingTopic?>(null) }

    val weeks: List<String> = listOf(
        "Week 1",
        "Week 2",
        "Week 3",
        "Week 4",
        "Week 5"
    )

    val topics = remember {
        mutableStateListOf<TrainingTopic>().apply {
            addAll(trainingViewModel.getTopics())
        }
    }

    val currentTopic = selectedTopic.value

    LaunchedEffect(Unit) {
        trainingViewModel.fetchRealNetworkUpdate()
        trainingViewModel.logFirebaseTrainingEvent()
        trainingViewModel.fetchFirebaseRemoteConfig()
    }

    if (currentTopic != null) {
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
                .padding(30.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Column {
                    Text(
                        text = "InternTrack",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Text(
                        text = "Training Progress App",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
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
                                        Color.Blue
                                    } else {
                                        Color.Red
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
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            item {
                ThreadReportCard()
            }

            item {
                CoroutineReportCard()
            }

            item {
                PaginationSampleCard(
                    posts = trainingViewModel.posts,
                    status = trainingViewModel.paginationStatus.value,
                    isLoading = trainingViewModel.isLoadingPosts.value,
                    onLoadMore = {
                        trainingViewModel.loadNextPostPage()
                    }
                )
            }

//            item {
//                DependencyInjectionCard(
//                    repositoryStatus = trainingViewModel.getRepositoryStatus(),
//                    topicCount = trainingViewModel.getTopicCount()
//                )
//            }

            topics.forEach { topic ->
                item {
                    TopicCard(
                        topic = topic,
                        onClick = {
                            selectedTopic.value = topic
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}