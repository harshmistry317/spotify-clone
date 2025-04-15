package com.harshm.spotifyclone.utils

import android.content.Context
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.harshm.spotifyclone.R
import com.harshm.spotifyclone.model.SongInfoDataModel

object Utils {
    fun getAllSongs() : List<SongInfoDataModel>{
        return listOf(
            SongInfoDataModel(
                id = "1",
                songName = "Dum Hai To Rok Ke Bata",
                movieName = "Pushpa 2",
                song = R.raw.dum_hai_to_rok_ke_bata,
                singerName = "Shreyas Talpade",
                poster = R.drawable.dum_hai_to_rok_ke_bata
            ),SongInfoDataModel(
                id = "2",
                songName = "Maaye",
                movieName = "Sky Force",
                song = R.raw.maaye_sky_force,
                singerName = "B Praak",
                poster = R.drawable.maaye_sky_force
            ),SongInfoDataModel(
                id = "3",
                songName = "Raanjhan",
                movieName = "Do Patti",
                song = R.raw.raanjhan_do_patti,
                singerName = "Parampara Tandon",
                poster = R.drawable.raanjhan_do_patti
            ),
        )
    }

    fun createSongUriFromResource(context: Context,@RawRes resourceId : Int) : String{
        val uri = Uri.Builder()
            .scheme("android.resource")
            .authority(context.packageName)
            .appendPath(resourceId.toString())
            .build()
        val uriString = uri.toString()
        return uriString
    }
    fun createImageUriFromResource(context: Context,@DrawableRes resourceId : Int) : String{
        val uri = Uri.Builder()
            .scheme("android.resource")
            .authority(context.packageName)
            .appendPath(resourceId.toString())
            .build()
        val uriString = uri.toString()
        return uriString
    }
}
