package com.piumini.interntrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.piumini.interntrack.data.TrainingTopic
import com.piumini.interntrack.ui.components.ProgressCircle

@Composable
fun TopicDetailScreen(
    topic: TrainingTopic,
    onBack:() -> Unit,
    onMarkComplete:() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(40.dp)
    ) {
        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text(text = "Back")
        }
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = topic.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                contentColor = Color.White
            )
        )
        {
            Column(
                modifier = Modifier.padding(22.dp)
            ) {
                ProgressCircle(progress = topic.progress)

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = topic.day,
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = topic.description,
                    fontSize = 16.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Task",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "• Learn Theory",
                    color = Color.Black
                )

                Text(
                    text = "• Build UI Component",
                    color = Color.Black
                )

                Text(
                    text = "• Run App",
                    color = Color.Black
                )

                Text(
                    text = "• Explain in Viva",
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Status: ${topic.status}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onMarkComplete,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )

        ) {
            Text(text = "Mark as Complete")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TopicDetailsScreenPreview() {
    TopicDetailScreen(
        topic = TrainingTopic(
            day = "Day 1",
            title = "Compose Advanced",
            description = "LazyColumn, LazyRow, Cards and Detail Screen",
            progress = 0.75f,
            status = "In Progress"
        ),
        onBack = {},
        onMarkComplete = {}
    )
}