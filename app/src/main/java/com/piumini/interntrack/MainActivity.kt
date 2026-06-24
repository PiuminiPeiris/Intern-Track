package com.piumini.interntrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.piumini.interntrack.ui.screens.HomeScreen
import com.piumini.interntrack.ui.screens.LoginScreen
import com.piumini.interntrack.ui.theme.InternTrackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InternTrackTheme {
                val isLoggedIn = remember {
                    mutableStateOf(false)
                }

                if (isLoggedIn.value) {
                    HomeScreen()
                } else {
                    LoginScreen(
                        onLoginSuccess = {
                            isLoggedIn.value = true
                        }
                    )
                }
            }
        }
    }
}
