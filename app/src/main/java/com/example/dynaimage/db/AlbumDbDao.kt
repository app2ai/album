package com.example.dynaimage.db

import androidx.paging.DataSource
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
    fun getTenPhotoAlbumAtOnce(): DataSource.Factory<Int, AlbumModelItem>

    @Query("Select * from tblAlbum LIMIT 10 OFFSET :currentIndex")
    suspend fun getAlbumInLegacyWay(currentIndex: Int): List<AlbumModelItem>?

    @Query("Select COUNT(id) From tblAlbum")
    suspend fun totalRecords(): Int
}