package com.harshm.spotifyclone.exoplayer.callbacks

import android.app.Notification
import android.content.Intent
import androidx.annotation.OptIn
import androidx.core.content.ContextCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerNotificationManager
import com.harshm.spotifyclone.exoplayer.MusicService
import com.harshm.spotifyclone.utils.Constance

@OptIn(UnstableApi::class)
class MusicPlayerNotificationListener(
    private val musicService: MusicService
) : PlayerNotificationManager.NotificationListener{

    override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
        super.onNotificationCancelled(notificationId, dismissedByUser)
        musicService.apply {
            stopForeground(false)
            isForeGroundService = false
            stopSelf()
        }
    }

    override fun onNotificationPosted(
        notificationId: Int,
        notification: Notification,
        ongoing: Boolean
    ) {
        super.onNotificationPosted(notificationId, notification, ongoing)
        musicService.apply {
            if (ongoing && !isForeGroundService){
                ContextCompat.startForegroundService(
                    this,
                    Intent(applicationContext,this::class.java)
                )
                startForeground(Constance.NOTIFICATION_MANAGER.NOTIFICATION_ID,notification)
                isForeGroundService = true
            }
        }
    }
}