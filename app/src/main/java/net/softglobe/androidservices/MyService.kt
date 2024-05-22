package net.softglobe.androidservices

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings
import androidx.core.app.NotificationCompat

class MyService : Service() {
    private lateinit var mediaPlayer : MediaPlayer
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            Actions.START.toString() -> {
                startMyService()
            }

            Actions.STOP.toString() -> {
                mediaPlayer.stop()
                stopSelf()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startMyService() {
        mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)
        mediaPlayer.start()
        mediaPlayer.isLooping = true
        val notification = NotificationCompat.Builder(
            this,"my_channel"
        ).setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Foreground Service")
            .setContentText("Foreground Service is running")
            .setOngoing(true)
            .build()
        startForeground(1001, notification)
    }
}

enum class Actions {
    START, STOP
}