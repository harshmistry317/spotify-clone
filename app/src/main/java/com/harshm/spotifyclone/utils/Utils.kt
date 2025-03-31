package com.harshm.spotifyclone.utils

import com.harshm.spotifyclone.R
import com.harshm.spotifyclone.model.SongInfoDataModel

object Utils {
    fun getAllSongs() : List<SongInfoDataModel>{
        return listOf(
            SongInfoDataModel(
                id = 1,
                songName = "Dum Hai To Rok Ke Bata",
                movieName = "Pushpa 2",
                song = R.raw.dum_hai_to_rok_ke_bata,
                singerName = "Shreyas Talpade"
            ),SongInfoDataModel(
                id = 2,
                songName = "Maaye",
                movieName = "Sky Force",
                song = R.raw.maaye_sky_force,
                singerName = "B Praak"
            ),
        )
    }
}
