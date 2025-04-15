package com.harshm.spotifyclone.exoplayer

import android.app.PendingIntent
import android.media.browse.MediaBrowser
import android.media.session.MediaSession

import android.os.Bundle
import android.service.media.MediaBrowserService
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.harshm.spotifyclone.exoplayer.callbacks.MusicPlayerNotificationListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import javax.inject.Inject

const val SERVICE_TAG = "SERVICE_TAG"
@AndroidEntryPoint
class MusicService : MediaBrowserService() {

    @Inject
    lateinit var dataSourceFactory : DefaultDataSource.Factory

    @Inject
    lateinit var exoPlayer : ExoPlayer
    private lateinit var musicNotificationManager: MusicNotificationManager

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    var isForeGroundService = false

    private lateinit var mediaSession : MediaSession
//    private lateinit var mediaSessionConnector : MediaSessionConnector

    private val mediaSessionCallback = object : MediaSession.Callback() {
        override fun onPlay() {
            exoPlayer.play()
            mediaSession.isActive = true
        }

        override fun onPause() {
            exoPlayer.pause()
        }

        override fun onStop() {
            exoPlayer.stop()
            stopSelf()
        }
    }

    override fun onCreate() {
        super.onCreate()
        initMediaSession()

    }

    private fun initMediaSession(){
        val activityIntent = packageManager?.getLaunchIntentForPackage(packageName)?.let {
            PendingIntent.getActivity(this,0,it, PendingIntent.FLAG_IMMUTABLE)
        }
        mediaSession = MediaSession(this,SERVICE_TAG).apply {
            setSessionActivity(activityIntent)
            setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS or MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS)
            setCallback(mediaSessionCallback)
            isActive = true

        }
        musicNotificationManager = MusicNotificationManager(
            context = this,
            sessionToken = mediaSession.sessionToken,
            notificationListener = MusicPlayerNotificationListener(this),
            newSongCallback = {

            }
        )
        sessionToken = mediaSession.sessionToken
    }


    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {

    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowser.MediaItem>>
    ) {

    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
        mediaSession.release()
        serviceScope.cancel()
    }
}