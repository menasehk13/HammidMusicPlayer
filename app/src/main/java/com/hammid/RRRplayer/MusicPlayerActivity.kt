package com.hammid.RRRplayer

import android.database.Cursor
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hammid.RRRplayer.Model.SongModel
import com.hammid.RRRplayer.databinding.ActivityPlayerBinding
import java.io.File


class MusicPlayerActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlayerBinding
 lateinit var arrayList: ArrayList<SongModel>
  var position:Int = 0
 var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val i = intent
        val bundle = i.extras
        arrayList = bundle!!.getParcelableArrayList<SongModel>("songs")!!
         position = bundle.getInt("selectedsong")

        binding.artistNamePalying.text=arrayList[position].artist_name
        binding.playingSongName.text=arrayList[position].song_name
        if(mediaPlayer!=null){
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
        }
        val uri=Uri.fromFile(File(arrayList[position].song_data))
        mediaPlayer=MediaPlayer.create(applicationContext,uri)
        mediaPlayer!!.start()
     binding.playSong.setOnClickListener {
         if (mediaPlayer!!.isPlaying){
             binding.playSong.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)

             mediaPlayer!!.pause()
         }else{
             binding.playSong.setImageResource(R.drawable.ic_baseline_pause_24)
             mediaPlayer!!.start()
         }
     }
        binding.nextSong.setOnClickListener {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            position = (position+1)%arrayList.size
            binding.artistNamePalying.text=arrayList[position].artist_name
            binding.playingSongName.text=arrayList[position].song_name
            val uri = Uri.parse(arrayList[position].song_data)
            mediaPlayer=MediaPlayer.create(applicationContext,uri)
            mediaPlayer!!.start()
            binding.playSong.setImageResource(R.drawable.ic_baseline_pause_24)
        }
   binding.previousSong.setOnClickListener {
       if (position==0){
           Toast.makeText(this, "First Song",Toast.LENGTH_SHORT).show()
       }else{
           mediaPlayer!!.stop()
           mediaPlayer!!.release()
           position = (position-1)%arrayList.size
           binding.artistNamePalying.text=arrayList[position].artist_name
           binding.playingSongName.text=arrayList[position].song_name
           val uri = Uri.parse(arrayList[position].song_data)
           mediaPlayer=MediaPlayer.create(applicationContext,uri)
           mediaPlayer!!.start()
           binding.playSong.setImageResource(R.drawable.ic_baseline_pause_24)
       }
      }
        binding.suffleMusic.setOnClickListener {
            binding.suffleMusic.
        }
    }

fun shuffleSong(random:Int){

}
    companion object{
        const val TAG = "SONG ACTIVITY"
    }
}