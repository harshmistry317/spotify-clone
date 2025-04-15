package com.harshm.spotifyclone.data.remote

import com.harshm.spotifyclone.model.SongInfoDataModel
import com.harshm.spotifyclone.utils.Utils

class MusicDataBase {

    fun getAllSongs(): List<SongInfoDataModel>{
        return try {
            Utils.getAllSongs()
        }catch (e : Exception){
            e.printStackTrace()
            emptyList()
        }
    }
}