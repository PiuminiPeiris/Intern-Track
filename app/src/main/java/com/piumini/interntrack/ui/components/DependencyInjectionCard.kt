package com.piumini.interntrack.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun DependencyInjectionCard(
    repositoryStatus: String,
    topicCount:Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Text(
                text = "Day 04",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Dependency Injection",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = repositoryStatus,
                fontSize = 14.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Data Flow: Repository → ViewModel → UI",
                fontSize = 14.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Topics loaded from repository: $topicCount",
                fontSize = 14.sp,
                color = Color.Black
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DependencyInjectionCardPreview() {
    DependencyInjectionCard(
        repositoryStatus = "TrainingRepository injected successfully using Hilt",
        topicCount = 5
    )
}