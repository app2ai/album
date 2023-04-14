package com.example.dynaimage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dynaimage.model.AlbumModelItem
import com.example.dynaimage.repo.AlbumRepository
import javax.inject.Inject

class AlbumListViewModel @Inject constructor(
    private var repo: AlbumRepository
): ViewModel() {
    private var _carsLiveData = MutableLiveData<List<AlbumModelItem>>()
    val carsLiveData: LiveData<List<AlbumModelItem>> = _carsLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    val progressLiveData: LiveData<Boolean> = _progressLiveData


}