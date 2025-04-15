package com.harshm.spotifyclone.exoplayer

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.session.MediaController
import android.media.session.MediaSession
import androidx.annotation.OptIn
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi

import androidx.media3.ui.PlayerNotificationManager
import androidx.media3.ui.PlayerNotificationManager.NotificationListener
import coil.Coil
import coil.imageLoader
import coil.request.ImageRequest
import com.harshm.spotifyclone.R
import com.harshm.spotifyclone.utils.Constance

@OptIn(UnstableApi::class)
class MusicNotificationManager
    (
    private val context: Context,
    sessionToken: MediaSession.Token,
    notificationListener: NotificationListener,
    private val newSongCallback: () -> Unit
) {
    private val notificationManager: PlayerNotificationManager
    private val mediaController = MediaController(context,sessionToken)

    init {

        notificationManager = PlayerNotificationManager.Builder(
            context,
            Constance.NOTIFICATION_MANAGER.NOTIFICATION_ID,
            Constance.NOTIFICATION_MANAGER.NOTIFICATION_CHANNEL_ID
        ).apply {
            setNotificationListener(notificationListener)
            setChannelNameResourceId(R.string.notification_channel_name)
            setChannelDescriptionResourceId(R.string.notification_channel_description)
            setSmallIconResourceId(R.drawable.ic_music)
            setMediaDescriptionAdapter(DescriptionAdapter(mediaController))

        }.build().apply {
            setMediaSessionToken(sessionToken)
        }

    }

    private inner class DescriptionAdapter(
        private val mediaController: MediaController
    ) : PlayerNotificationManager.MediaDescriptionAdapter{
        override fun getCurrentContentTitle(player: Player): CharSequence {
            return mediaController.metadata?.description?.title.toString()
        }

        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            return mediaController.sessionActivity
        }

        override fun getCurrentContentText(player: Player): CharSequence? {
            return mediaController.metadata?.description?.subtitle.toString()
        }

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {
            val request = ImageRequest.Builder(context)
                .data(mediaController.metadata?.description?.iconUri)
                .target(
                    onSuccess = { result: Drawable ->
                        // Convert Drawable to Bitmap if needed
                        val bitmap = (result as BitmapDrawable).bitmap
                        // Handle the bitmap (similar to onResourceReady)
                        callback.onBitmap(bitmap)
                    },
                    onError = {
                        // Handle error
                    },
                    onStart = {
                        // Placeholder or similar to onLoadCleared
                    }
                )
                .build()

            context.imageLoader.enqueue(request)
            return null
        }

    }

    fun showNotification(player: Player){
        notificationManager.setPlayer(player)
    }
}