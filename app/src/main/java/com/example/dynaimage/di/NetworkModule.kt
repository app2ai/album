package com.example.dynaimage.di

import com.example.dynaimage.service.AlbumApiService
import com.sevenpeakssoftware.vishalr.service.RetrofitClient
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    fun provideAlbumApiService(): AlbumApiService {
        return RetrofitClient.apiService
    }
}