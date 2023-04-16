package com.example.dynaimage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dynaimage.model.AlbumModelItem
import com.example.dynaimage.repo.AlbumRepository
import com.example.dynaimage.repo.ApiFailed
import com.example.dynaimage.repo.ApiSuccess
import com.example.dynaimage.repo.SocketTimeout
import com.example.dynaimage.utils.SingleLiveDataEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlbumListViewModel @Inject constructor(
    private var repo: AlbumRepository
): ViewModel() {

    private var _albumLiveData = SingleLiveDataEvent<List<AlbumModelItem>>()
    val albumLiveData: SingleLiveDataEvent<List<AlbumModelItem>> = _albumLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    val progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _noDataStatusLiveData = MutableLiveData<Boolean>()
    val noDataStatusLiveData: LiveData<Boolean> = _noDataStatusLiveData

    fun setProgressVisibility(isProgress: Boolean) {
        _progressLiveData.value = isProgress
    }

    fun checkIfDbIsEmpty() {
        viewModelScope.launch {
            val count = repo.getRecordsCount()
            _noDataStatusLiveData.value = count < 1
        }
    }

    fun fetchPhotoAlbum() {
        // fetch data remotely
        viewModelScope.launch {
            when(val responseStatus = repo.downloadListOfPhoto()) {
                is ApiSuccess -> {
                    repo.savePhotosInDb(responseStatus.albums ?: listOf())
                    _noDataStatusLiveData.value = false
                }
                ApiFailed -> {
                }
                SocketTimeout -> {
                }
            }
        }
    }

    // get 10 pages at once
    fun catchPhotosPageWise(index: Int) {
        viewModelScope.launch {
            _progressLiveData.value = true
            delay(750L)
            _albumLiveData.value = repo.getTenAlbumLegacyWay(index)
            _progressLiveData.value = false
        }
    }
}