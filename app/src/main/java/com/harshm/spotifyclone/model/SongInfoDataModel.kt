package com.harshm.spotifyclone.model

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

data class SongInfoDataModel(
    val id : String,
    val songName : String,
    val movieName : String,
    @RawRes val song : Int,
    val singerName : String,
    @DrawableRes val poster : Int
)