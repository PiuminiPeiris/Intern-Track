package com.piumini.interntrack.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.Job

data class TrainingSyncResult(
    val message: String,
    val dispatcherThread: String,
    val syncedTime: String
)

@Composable
fun CoroutineReportCard() {
    val scope = rememberCoroutineScope()

    val syncMessage = remember {
        mutableStateOf("No data synced yet")
    }

    val isSyncing = remember {
        mutableStateOf(false)
    }

    val syncStatus = remember {
        mutableStateOf("Ready")
    }

    val mainThreadName = remember {
        mutableStateOf(Thread.currentThread().name)
    }

    val dispatcherThreadName = remember {
        mutableStateOf("Not started")
    }

    val syncedTime = remember {
        mutableStateOf("Not synced")
    }

    val progressValue = remember {
        mutableStateOf(0.6f)
    }

    val syncJob = remember {
        mutableStateOf<Job?>(null)
    }

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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.size(18.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Day 03",
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Status: ${syncStatus.value}",
                        fontSize = 13.sp,
                        color = Color.Blue,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            isSyncing.value = true
                            syncStatus.value = "Syncing..."
                            syncMessage.value = "Generating coroutine report..."
                            progressValue.value = 0.75f

                            syncJob.value = scope.launch {
                                val result: TrainingSyncResult = withContext(Dispatchers.IO) {
                                    delay(3000)

                                    val time = SimpleDateFormat(
                                        "hh:mm:ss a",
                                        Locale.getDefault()
                                    ).format(Date())

                                    TrainingSyncResult(
                                        message = """
                                            Training data synced successfully
                                            Day 01: Compose Advanced - Completed
                                            Day 02: Android Threads - Completed
                                            Day 03: Coroutines - In Progress
                                        """.trimIndent(),
                                        dispatcherThread = Thread.currentThread().name,
                                        syncedTime = time
                                    )
                                }

                                syncMessage.value = result.message
                                dispatcherThreadName.value = result.dispatcherThread
                                syncedTime.value = result.syncedTime
                                mainThreadName.value = Thread.currentThread().name
                                syncStatus.value = "Completed"
                                progressValue.value = 1.0f
                                isSyncing.value = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isSyncing.value,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF9C27B0),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = if (isSyncing.value) {
                                "Syncing..."
                            } else {
                                "Sync Training Data"
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            syncJob.value?.cancel()
                            isSyncing.value = false
                            syncStatus.value = "Cancelled"
                            syncMessage.value = "Sync cancelled by user"
                            progressValue.value = 0.6f
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = isSyncing.value,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Cancel Sync")
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            syncJob.value = null
                            isSyncing.value = false
                            syncStatus.value = "Ready"
                            syncMessage.value = "No data synced yet"
                            dispatcherThreadName.value = "Not started"
                            syncedTime.value = "Not synced"
                            progressValue.value = 0.6f
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Reset")
                    }

                    Text(
                        text = syncMessage.value,
                        fontSize = 14.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Main Thread: ${mainThreadName.value}",
                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )

                    Text(
                        text = "IO Dispatcher Thread: ${dispatcherThreadName.value}",
                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )

                    Text(
                        text = "Synced Time: ${syncedTime.value}",
                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CoroutineReportCardPreview() {
    CoroutineReportCard()
}