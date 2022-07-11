package com.hammid.RRRplayer.Model

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class SongModel(
    val song_id:Long? = null,
    val song_name:String? = null,
    val artist_name:String? = null,
    val album_name:String? = null,
    val song_data:String?= null,
    val date:Long?=null,
    val album_image:Uri?=null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readParcelable(Uri::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(song_id)
        parcel.writeString(song_name)
        parcel.writeString(artist_name)
        parcel.writeString(album_name)
        parcel.writeString(song_data)
        parcel.writeValue(date)
        parcel.writeParcelable(album_image, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SongModel> {
        override fun createFromParcel(parcel: Parcel): SongModel {
            return SongModel(parcel)
        }

        override fun newArray(size: Int): Array<SongModel?> {
            return arrayOfNulls(size)
        }
    }
}
