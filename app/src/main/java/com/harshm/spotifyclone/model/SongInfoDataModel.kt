package com.harshm.spotifyclone.model

import androidx.annotation.RawRes

data class SongInfoDataModel(
    val id : Int,
    val songName : String,
    val movieName : String,
    @RawRes val song : Int,
    val singerName : String
)