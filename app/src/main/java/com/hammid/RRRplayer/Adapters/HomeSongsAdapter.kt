package com.hammid.RRRplayer.Adapters

import android.content.Context
import android.content.Intent
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hammid.RRRplayer.Model.SongModel
import com.hammid.RRRplayer.MusicPlayerActivity
import com.hammid.RRRplayer.R
import com.hammid.RRRplayer.databinding.CustomSongListBinding
import com.squareup.picasso.Picasso

class HomeSongsAdapter(val context: Context,var list: ArrayList<SongModel>):
    RecyclerView.Adapter<HomeSongsAdapter.viewHolder>() {
     class viewHolder(itemview: View):RecyclerView.ViewHolder(itemview){
         val image_view = itemView.findViewById<ImageView>(R.id.image_album_song)
         val sondTitle = itemview.findViewById<TextView>(R.id.song_name_item)
         val artistname= itemview.findViewById<TextView>(R.id.artist_name_song_list)
         val layoutLinear = itemview.findViewById<LinearLayout>(R.id.linearLayout_click)
     }
     fun getData(listitem:ArrayList<SongModel>){
         this.list=listitem
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_song_list,parent,false)
      return  viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
            holder.artistname.text=list[position].artist_name
            holder.sondTitle.text=list[position].song_name
            Picasso.get().load(list[position].album_image).fit().into(holder.image_view)
        holder.layoutLinear.setOnClickListener {
            context.startActivity(Intent(context.applicationContext,MusicPlayerActivity::class.java)
                .putExtra("songs",list)
                .putExtra("selectedsong", position)
                .putExtra("songname",list[position].song_name))
            Log.d("TAG selected item number", "onBindViewHolder: $position")
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}