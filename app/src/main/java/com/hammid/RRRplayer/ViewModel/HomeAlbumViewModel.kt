package com.hammid.RRRplayer.ViewModel

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hammid.RRRplayer.Model.SongModel

class HomeAlbumViewModel:ViewModel() {
    val listAlbum = MutableLiveData<ArrayList<SongModel>>()

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMusic(context: Context){
        val contentProvider = context.contentResolver
        val songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val Cursor = contentProvider.query(songUri,null,null,null)
        if (Cursor!= null) {
            val songId = Cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val songTitle = Cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val songArtist = Cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val songData = Cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            val date = Cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED)
            val albumId = Cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
            val album=Cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
            while (Cursor.moveToNext()){
                val currentSongId = Cursor.getLong(songId)
                val currentSongTitle= Cursor.getString(songTitle)
                val currentSongArtist = Cursor.getString(songArtist)
                val currentSongAlbum =Cursor.getString(album)
                val currentSongData = Cursor.getString(songData)
                val currentSongDate = Cursor.getLong(date)
                val currentSongAlbumId = Cursor.getLong(albumId)
                val image_URI = Uri.parse("content://media/external/audio/albumart")
                val albumUri = ContentUris.withAppendedId(image_URI,currentSongAlbumId)
                val musicData= SongModel(currentSongId,currentSongTitle,currentSongArtist,currentSongAlbum,currentSongData,currentSongDate,albumUri)
                listAlbum.value!!.add(musicData)
            }
        }

    }
}