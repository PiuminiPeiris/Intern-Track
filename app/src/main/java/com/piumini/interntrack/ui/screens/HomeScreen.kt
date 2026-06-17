package com.piumini.interntrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableStateListOf
import com.piumini.interntrack.ui.components.ThreadReportCard

@Composable
fun HomeScreen() {
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
        mutableStateListOf(
            TrainingTopic(
                day = "Day 1",
                title = "Compose Advance",
                description = "Lazy Column",
                progress = 0.9f,
                status = "In Progress"
            ),TrainingTopic(
                day = "Day 2",
                title = "Compose Advance",
                description = "Lazy Column",
                progress = 0.9f,
                status = "In Progress"
            ) ,
            TrainingTopic(
                day = "Day 3",
                title = "Compose Advance",
                description = "Lazy Column",
                progress = 0.9f,
                status = "In Progress"
            ),
            TrainingTopic(
                day = "Day 4",
                title = "Compose Advance",
                description = "Lazy Column",
                progress = 0.9f,
                status = "In Progress"
            ),
            TrainingTopic(
                day = "Day 5",
                title = "Compose Advance",
                description = "Lazy Column",
                progress = 0.9f,
                status = "In Progress"
            )
        )
    }

    val currentTopic = selectedTopic.value

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(40.dp)
        ) {
            Text(
                text = "InternTrack",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Training Progress App",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(22.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(weeks) { week->
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
                            contentColor = if (selectedWeek.value == week) {
                                Color(0xFFFFFFFF)
                            } else {
                                Color(0xFFFFFFFF)
                            }
                        )
                    ) {
                        Text(text = week)
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "${selectedWeek.value} Topics",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(20.dp))

            ThreadReportCard()

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(topics){ topic ->
                    TopicCard(
                        topic = topic,
                        onClick = {
                            selectedTopic.value = topic
                        })
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