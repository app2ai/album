package com.sevenpeakssoftware.vishalr.service

import com.example.dynaimage.service.AlbumApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// Retrofit client object
@Singleton
object RetrofitClient {
    const val myDomain = "https://jsonplaceholder.typicode.com"
    val apiService: AlbumApiService
        get(){
            return Retrofit.Builder()
                .baseUrl(myDomain)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AlbumApiService::class.java)
        }

    private val okHttpClient: OkHttpClient
        get(){
            return OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .build()
        }
}