package com.example.dynaimage.di

import android.content.Context
import com.example.dynaimage.db.AlbumDbDao
import com.example.dynaimage.db.AlbumRoomDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    fun provideAlbumDbDao(context: Context): AlbumDbDao {
        return AlbumRoomDatabase.getDbInstance(context).albumDao()
    }
}