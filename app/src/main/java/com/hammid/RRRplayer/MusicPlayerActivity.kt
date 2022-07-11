package com.hammid.RRRplayer

import android.content.res.ColorStateList
import android.database.Cursor
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.hammid.RRRplayer.Model.SongModel
import com.hammid.RRRplayer.databinding.ActivityPlayerBinding
import java.io.File
import java.lang.Thread.sleep
import java.util.*
import kotlin.collections.ArrayList


class MusicPlayerActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlayerBinding
 lateinit var arrayList: ArrayList<SongModel>
  var position:Int = 0
 var mediaPlayer: MediaPlayer? = null
    var shuffleison = false
    lateinit var updateseekbar:Thread
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

        updateseekbar = Thread(){
            run {
                val totalduration = mediaPlayer!!.duration
                var currentPos=0
                while (currentPos<totalduration){
                    try {
                        sleep(500)
                        currentPos = mediaPlayer!!.currentPosition
                        binding.seekProgress.progress=currentPos
                    }catch (e:InterruptedException){
                        e.printStackTrace()
                    }
                }
            }
        }
        binding.seekProgress.max = mediaPlayer!!.duration
        updateseekbar.start()

         val endtime = showTime(mediaPlayer!!.duration)
        binding.totaltime.text=endtime
         val handler = Handler()
        handler.postDelayed(object :Runnable{
            override fun run() {
                val time = showTime(mediaPlayer!!.currentPosition)
                binding.starttime.text=time
                handler.postDelayed(this,1000)
            }

        },1000)



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
        binding.shuffleSong.setOnClickListener {
           if (!shuffleison){
               shuffleison=true
               binding.shuffleSong.setColorFilter(R.color.red)
               Toast.makeText(this, "Shuffle is on", Toast.LENGTH_SHORT).show()
               shuffleSong()
           }else{
               Toast.makeText(this, "Shuffle is off", Toast.LENGTH_SHORT).show()
                binding.nextSong.setOnClickListener {
                    nextsong()
                }
               shuffleison=false
               binding.shuffleSong.setColorFilter(R.color.black)
           }
        }
        mediaPlayer!!.setOnCompletionListener { binding.nextSong.performClick()}

    }

    private fun showTime(duration:Int): String {
        var time = ""
        val min = duration/1000/60
        val sec = duration/1000%60
        time+=min.toString() +":"
        if (sec <10){
            time+="0"
        }
        time+=sec
        return time
    }

    fun nextsong(){
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
fun shuffleSong(){
    binding.nextSong.setOnClickListener {
        mediaPlayer!!.stop()
        mediaPlayer!!.release()
        val r = Random()
        position = r.nextInt(arrayList.size)
        binding.artistNamePalying.text=arrayList[position].artist_name
        binding.playingSongName.text=arrayList[position].song_name
        val uri = Uri.parse(arrayList[position].song_data)
        mediaPlayer=MediaPlayer.create(applicationContext,uri)
        mediaPlayer!!.start()
        binding.playSong.setImageResource(R.drawable.ic_baseline_pause_24)
    }
}
    companion object{
        const val TAG = "SONG ACTIVITY"
    }
}