package com.hammid.RRRplayer.Model

import android.net.Uri

data class SongModel(
    val song_id:Long? = null,
    val song_name:String? = null,
    val artist_name:String? = null,
    val album_name:String? = null,
    val song_data:String?= null,
    val date:Long?=null,
    val album_image:Uri?=null
)
