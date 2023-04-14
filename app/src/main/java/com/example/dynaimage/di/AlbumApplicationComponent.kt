package com.example.dynaimage.di

import android.content.Context
import com.example.dynaimage.MainActivity
import com.example.dynaimage.views.AlbumListFragment
import com.example.dynaimage.views.DetailedFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

// App component dependency provider
@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class, DatabaseModule::class])
interface AlbumApplicationComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: AlbumListFragment)
    fun inject(fragment: DetailedFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AlbumApplicationComponent
    }
}