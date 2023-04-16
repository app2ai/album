package com.example.dynaimage.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class AlbumModel : ArrayList<AlbumModelItem>()

@Entity(tableName = "tblAlbum")
@Parcelize
data class AlbumModelItem(
    val albumId: Int,
    @PrimaryKey
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
): Parcelable