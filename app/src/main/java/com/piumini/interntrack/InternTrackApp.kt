package com.piumini.interntrack

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.androidnetworking.AndroidNetworking

@HiltAndroidApp
class InternTrackApp : Application() {
    override fun onCreate() {
        super.onCreate()

        AndroidNetworking.initialize(applicationContext)
    }
}