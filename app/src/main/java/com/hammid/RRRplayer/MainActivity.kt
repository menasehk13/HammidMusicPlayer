package com.hammid.RRRplayer

import android.content.ContentUris
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hammid.RRRplayer.Adapters.HomeAlbumAdapter
import com.hammid.RRRplayer.Adapters.HomeSongsAdapter
import com.hammid.RRRplayer.Helper.CheckPermisson
import com.hammid.RRRplayer.Model.SongModel
import com.hammid.RRRplayer.ViewModel.HomeAlbumViewModel
import com.hammid.RRRplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var homeAlbumViewModel: HomeAlbumViewModel
    lateinit var homeAlbumAdapter: HomeAlbumAdapter
    lateinit var homeSongsAdapter: HomeSongsAdapter
    var listmusic:ArrayList<SongModel> = ArrayList<SongModel>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CheckPermisson(this,this)
            .checkPer()
        homeAlbumViewModel=ViewModelProvider(this).get(HomeAlbumViewModel::class.java)
        initview()
        getMusic()
    }
       @RequiresApi(Build.VERSION_CODES.O)
       fun getMusic(){
           val contentProvider = this.contentResolver
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
                  listmusic.add(musicData)
                   homeSongsAdapter.getData(listmusic)
               }
           }
       }

    private fun initview() {
    homeSongsAdapter= HomeSongsAdapter(this, listmusic)
    binding.recycleviewSong.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    binding.recycleviewSong.adapter=homeSongsAdapter
    binding.recycleviewSong.hasFixedSize()
    }
    companion object{
        const val TAG ="MAIN ACTIVE"
    }
}