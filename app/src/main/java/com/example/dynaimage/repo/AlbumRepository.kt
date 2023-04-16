package com.example.dynaimage.repo

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.dynaimage.db.AlbumDbDao
import com.example.dynaimage.model.AlbumModelItem
import com.example.dynaimage.service.AlbumApiService
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val service: AlbumApiService,
    private val dao: AlbumDbDao
) {
    suspend fun downloadListOfPhoto(): AlbumAPIResponse {
        return try {
            val call = service.albumApi()
            if (call.isSuccessful) {
                val albums = call.body()
                ApiSuccess(albums)
            } else {
                ApiFailed
            }
        } catch (sException: Exception) {
            SocketTimeout
        }
    }

    suspend fun getAllPagedPhotos(config: PagedList.Config): LiveData<PagedList<AlbumModelItem>> {
        val factory = dao.getTenPhotoAlbumAtOnce()
        return LivePagedListBuilder(factory, config).build()
    }

    suspend fun getTenAlbumLegacyWay(index: Int) : List<AlbumModelItem>?{
        return dao.getAlbumInLegacyWay(index)
    }

    suspend fun savePhotosInDb(photoAlbums: List<AlbumModelItem>) {
        for (photoAlbum in photoAlbums) {
            dao.addPhoto(photoAlbum)
        }
    }

    suspend fun getRecordsCount() : Int {
        return dao.totalRecords()
    }
}

// API Response sealed status
sealed class AlbumAPIResponse
data class ApiSuccess(val albums: List<AlbumModelItem>?) : AlbumAPIResponse()
object ApiFailed : AlbumAPIResponse()
object SocketTimeout : AlbumAPIResponse()