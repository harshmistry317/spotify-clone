package com.harshm.spotifyclone.exoplayer

import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.source.ConcatenatingMediaSource
import androidx.media3.exoplayer.source.ConcatenatingMediaSource2
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.ProgressiveMediaSource

import com.harshm.spotifyclone.data.remote.MusicDataBase
import com.harshm.spotifyclone.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class AppMusicSource @Inject constructor(
    private val musicDataBase: MusicDataBase,
    private val context: Context
) {

    // video 6
    private var songs  = emptyList<MediaMetadataCompat>()

    // video 6
    suspend fun fetchMediaData() = withContext(Dispatchers.IO){

        state = State.STATE_INITIALIZING
        val allSongs = musicDataBase.getAllSongs()
        songs = allSongs.map { song->
            MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST,song.singerName)
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID,song.id)
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE,song.songName)
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE,song.songName)
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI,Utils.createImageUriFromResource(context,song.poster))
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI,Utils.createSongUriFromResource(context,song.song))
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI,Utils.createImageUriFromResource(context,song.poster))
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE,song.singerName)
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION,song.movieName)
                .build()
        }
        state = State.STATE_INITIALIZED
    }

    // video 6
    fun asMediaSource(dataSourceFactory: DefaultDataSource.Factory): List<MediaItem> {
        return songs.map { song ->
            MediaItem.fromUri(song.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI))
        }
    }

//    fun asMediaSource(dataSourceFactory: DefaultDataSource.Factory) : ConcatenatingMediaSource {
//        val concatenatingMediaSource2 = ConcatenatingMediaSource()
//        songs.forEach { song->
//            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
//                .createMediaSource(MediaItem.fromUri(song.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI)))
//            concatenatingMediaSource2.addMediaSource(mediaSource)
//
//        }
//        return  concatenatingMediaSource2
//    }

    // video 6

    fun asMediaItems() = songs.map {  song->
        val desc = MediaDescriptionCompat.Builder()
            .setMediaUri(song.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI).toUri())
            .setTitle(song.description.title)
            .setSubtitle(song.description.subtitle)
            .setMediaId(song.description.mediaId)
            .setIconUri(song.description.iconUri)
            .build()

        MediaBrowserCompat.MediaItem(desc, MediaBrowserCompat.MediaItem.FLAG_PLAYABLE)

    }




    // video 5
    private val onReadyListeners = mutableListOf<(Boolean) -> Unit>()

    // video 5
    private var state : State = State.STATE_CREATED
        set(value){
            if (value == State.STATE_INITIALIZED || value == State.STATE_ERROR){
                synchronized(onReadyListeners){
                    field = value
                    onReadyListeners.forEach{ listener->
                        listener(state == State.STATE_INITIALIZED)

                    }
                }
            } else {
                field = value
            }
        }


    // video 5
    fun whenReady(action: (Boolean) -> Unit) : Boolean {
        if (state == State.STATE_CREATED || state == State.STATE_INITIALIZING){
            onReadyListeners += action
            return false
        } else {
            action(state == State.STATE_INITIALIZED)
            return true
        }
    }
}

// video 5
enum class State {
    STATE_CREATED,
    STATE_INITIALIZING,
    STATE_INITIALIZED,
    STATE_ERROR
}