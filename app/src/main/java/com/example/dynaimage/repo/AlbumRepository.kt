package com.example.dynaimage.repo

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

    suspend fun getAllPhotos(): List<AlbumModelItem> {
        return dao.getTenPhotoAlbumAtOnce() ?: listOf()
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