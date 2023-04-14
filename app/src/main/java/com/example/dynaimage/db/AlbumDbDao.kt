package com.example.dynaimage.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dynaimage.model.AlbumModelItem

@Dao
interface AlbumDbDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhoto(user: AlbumModelItem): Long

    @Query("Select * from tblAlbum")
    suspend fun getTenPhotoAlbumAtOnce(): List<AlbumModelItem>?

    @Query("Select COUNT(id) From tblAlbum")
    suspend fun totalRecords(): Int
}