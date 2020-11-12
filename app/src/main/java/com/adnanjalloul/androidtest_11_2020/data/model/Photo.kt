package com.adnanjalloul.androidtest_11_2020.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("albumId")
    var albumId: Int = 0,
    @SerializedName("id")
    var photoId: Int = 0,
    @SerializedName("title")
    var title: String = "",
    @SerializedName("url")
    var photoUrl: String = "",
    @SerializedName("thumbnailUrl")
    var thumbnailUrl: String = ""
): Parcelable {

    constructor(parcel: Parcel) : this() {
        albumId = parcel.readInt()
        photoId = parcel.readInt()
        title = parcel.readString()?:""
        photoUrl = parcel.readString()?:""
        thumbnailUrl = parcel.readString()?:""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(albumId)
        parcel.writeInt(photoId)
        parcel.writeString(title)
        parcel.writeString(photoUrl)
        parcel.writeString(thumbnailUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Photo> {
        override fun createFromParcel(parcel: Parcel): Photo {
            return Photo(parcel)
        }

        override fun newArray(size: Int): Array<Photo?> {
            return arrayOfNulls(size)
        }
    }
}