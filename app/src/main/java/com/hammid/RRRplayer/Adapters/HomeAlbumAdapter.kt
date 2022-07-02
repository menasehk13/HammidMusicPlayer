package com.hammid.RRRplayer.Adapters

import android.content.Context
import android.graphics.Picture
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hammid.RRRplayer.Model.SongModel
import com.hammid.RRRplayer.R
import com.hammid.RRRplayer.databinding.CustomLayoutAlbumBinding
import com.squareup.picasso.Picasso

class HomeAlbumAdapter(val context:Context, var list:List<SongModel>):RecyclerView.Adapter<HomeAlbumAdapter.viewholder>() {
   inner class viewholder(val binding: CustomLayoutAlbumBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val binding = CustomLayoutAlbumBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return viewholder(binding)
    }
    fun setData(listadd:List<SongModel>){
        this.list=listadd
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.binding.AblumName.text=list[position].album_name
        holder.binding.artistName.text=list[position].artist_name
        if (list[position].album_image != null){
            Picasso.get().load(list[position].album_image).fit().into(holder.binding.imageAlbum)
        }else{
            holder.binding.imageAlbum.setImageResource(context.resources.getIdentifier("musicimage","drawable",context.packageName))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}