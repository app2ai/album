package com.example.dynaimage.service

import com.example.dynaimage.model.AlbumModel
import retrofit2.Response
import retrofit2.http.GET

interface AlbumApiService {

    @GET("/photos")
    suspend fun albumApi(): Response<AlbumModel?>
}
