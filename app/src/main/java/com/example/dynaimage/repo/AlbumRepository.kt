package com.example.dynaimage.repo

import com.example.dynaimage.db.AlbumDbDao
import com.example.dynaimage.model.AlbumModelItem
import com.example.dynaimage.service.AlbumApiService
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val service: AlbumApiService,
    private val carDao: AlbumDbDao
) {
    suspend fun downloadListOfCar(): AlbumAPIResponse {
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

    suspend fun getAllCars(): List<AlbumModelItem> {
        return carDao.getAllCars() ?: listOf()
    }

    suspend fun saveCarsInDb(cars: List<AlbumModelItem>) {
        for (car in cars) {
            carDao.addCar(car)
        }
    }
}

// API Response sealed status
sealed class AlbumAPIResponse
data class ApiSuccess(val cars: List<AlbumModelItem>?) : AlbumAPIResponse()
object ApiFailed : AlbumAPIResponse()
object SocketTimeout : AlbumAPIResponse()