package com.example.trackie_fyp.notification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.trackie_fyp.R

class MyApplication : Application() {

    companion object {
        const val CHANNEL_ID = "budget_alerts_channel"
    }

}