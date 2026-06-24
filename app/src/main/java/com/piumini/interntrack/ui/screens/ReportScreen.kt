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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReportScreen (
    onBack: () -> Unit,
    fastNetworkingStatus: String
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 56.dp,
                bottom = 24.dp
            ),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){
        item {
            Row(
                Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.DarkGray
                    )
                }
                Text(
                    text = "Report",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Report Generated Successfully",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "InternTrack training progress summary",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }
        item {
            ReportInfoCard(
                title = "Week 3 Progress",
                value = "3 topics completed / in progress",
                description = "Compose Advanced, Android Threads and Coroutines were practiced."
            )
        }

        item {
            ReportInfoCard(
                title = "Fast Android Networking",
                value = fastNetworkingStatus,
                description = "Sample API request completed using Fast Android Networking library."
            )
        }

        item {
            ReportInfoCard(
                title = "Completed Parts",
                value = "Android Threads • Retrofit • Firebase",
                description = "Core Android intern training features are working successfully."
            )
        }
        item {
            ReportInfoCard(
                title = "Current Status",
                value = "In Progress",
                description = "More topics can be loaded using the Load More Topics feature."
            )
        }
    }
}

@Composable
fun ReportInfoCard(
    title: String,
    value: String,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7F0093)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ReportScreenPreview() {
    ReportScreen(
        onBack = {},
        fastNetworkingStatus = "Fast Android Networking loaded successfully"
    )
}