package com.piumini.interntrack

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings

@Singleton
class FirebaseService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val firebaseAnalytics:FirebaseAnalytics =
        FirebaseAnalytics.getInstance(context)

    fun logTrainingHomeOpened() {
        val bundle = Bundle().apply {
            putString("screen_name", "TrainingHome")
            putString("training_week", "week 4")
        }

        firebaseAnalytics.logEvent("training_home_opened", bundle)

        Log.d(
            "InternTrackFirebase",
            "Analytics event logged: training_home_opened"
        )
    }

    fun fetchRemoteConfig() {
        val remoteConfig = FirebaseRemoteConfig.getInstance()

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }

        remoteConfig.setConfigSettingsAsync(configSettings)

        remoteConfig.setDefaultsAsync(
            mapOf(
                "welcome_message" to "Welcome to InternTrack!",
                "week_status" to "Week 4 Status"
            )
        ).addOnCompleteListener {
            remoteConfig.fetchAndActivate()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val welcomeMessage =
                            remoteConfig.getString("welcome_message")
                        val weekStatus =
                            remoteConfig.getString("week_status")

                        Log.d(
                            "InternTrackFirebase",
                            "Welcome Message: $welcomeMessage | $weekStatus"
                        )
                    }else {
                        Log.e(
                            "InternTrackFirebase",
                            "Config fetch failed",
                            task.exception
                        )
                    }
                }
        }
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val welcomeMessage =
                        remoteConfig.getString("welcome_message")
                    val weekStatus =
                        remoteConfig.getString("week_status")

                    Log.d(
                        "InternTrackFirebase",
                        "Welcome Message: $welcomeMessage | $weekStatus"
                    )
                } else {
                    Log.e(
                        "InternTrackFirebase",
                        "Config fetch failed",
                        task.exception
                    )
                }
            }
    }
}