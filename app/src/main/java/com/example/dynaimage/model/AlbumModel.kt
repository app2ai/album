package com.example.dynaimage.model

import androidx.room.Entity
import androidx.room.PrimaryKey

class AlbumModel : ArrayList<AlbumModelItem>()

@Entity(tableName = "tblAlbum")
data class AlbumModelItem(
    @PrimaryKey
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)