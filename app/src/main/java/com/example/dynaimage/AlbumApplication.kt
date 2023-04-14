package com.example.dynaimage

import android.app.Application
import com.example.dynaimage.di.AlbumApplicationComponent
import com.example.dynaimage.di.DaggerAlbumApplicationComponent

class AlbumApplication : Application() {
    lateinit var appComponent: AlbumApplicationComponent
    override fun onCreate() {
        super.onCreate()
        // Initialise app component here
        appComponent = DaggerAlbumApplicationComponent.factory().create(applicationContext)
    }
}