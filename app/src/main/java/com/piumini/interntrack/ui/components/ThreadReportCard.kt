package com.piumini.interntrack.ui.components

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import java.util.Date
import java.util.Locale

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat

@Composable
fun ThreadReportCard() {
    val reportText = remember {
        mutableStateOf("No report generated yet")
    }

    val isLoading = remember {
        mutableStateOf(false)
    }

    val threadStatus = remember {
        mutableStateOf("Ready")
    }

    val mainHandle = remember {
        Handler(Looper.getMainLooper())
    }

    val workerThreadName = remember {
        mutableStateOf("Not started")
    }

    val mainThreadName = remember {
        mutableStateOf(Thread.currentThread().name)
    }

    val generatedTime = remember {
        mutableStateOf("Not generated")
    }

    val progressValue = remember {
        mutableStateOf(0.5f)
    }

    val mainHandler = remember {
        Handler(Looper.getMainLooper())
    }

    Card(
        modifier = Modifier.fillMaxWidth() ,
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            contentColor = Color.Black,
            containerColor = Color.White
        )
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Day 02",
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Android Threads",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Generate progress report using background thread",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = if (isLoading.value) {
                            "Status: Running..."
                        } else {
                            "Status: Ready"
                        },
                        fontSize = 13.sp,
                        color = Color.Blue,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            isLoading.value = true
                            threadStatus.value = "Running...."
                            reportText.value = "Generating report"
                            progressValue.value = 0.7f

                            Thread {
                                val workerName = Thread.currentThread().name

                                Thread.sleep(3000)

                                val time = SimpleDateFormat(
                                    "hh:mm:ss a",
                                    Locale.getDefault()
                                ).format(Date())

                                val generatedReport = """
                                    InternTrack Report
                                    Day 01: Compose Advanced - Completed
                                    Day 02: Android Threads - In Progress
                                    Status: Background thread executed successfully
                                """.trimIndent()

                                mainHandle.post {
                                    reportText.value = generatedReport
                                    generatedTime.value = time
                                    threadStatus.value = "Complete"
                                    progressValue.value = 1.0f
                                    isLoading.value = false
                                }
                            }.start()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading.value,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Blue,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = if (isLoading.value) {
                                "Please wait"
                            } else {
                                "Generate Report"
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = reportText.value,
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
                        text = "Worker Thread: ${workerThreadName.value}",
                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )

                    Text(
                        text = "Generated Time: ${generatedTime.value}",
                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun ThreadReportCardPreview() {
    ThreadReportCard()
}