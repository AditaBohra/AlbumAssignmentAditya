package com.example.albumassignment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.albumassignment.api.model.Album
import com.example.albumassignment.api.model.Photo
import com.example.albumassignment.api.Repo
import com.example.albumassignment.api.Result
import com.example.albumassignment.ui.OnItemClick
import com.example.albumassignment.ui.PhotosFragment
import kotlinx.coroutines.launch

class ActivityViewModel(private val repo: Repo) : ViewModel(), OnItemClick {

    private val _albums: MutableLiveData<List<Album>> = MutableLiveData()
    val albums: LiveData<List<Album>>
        get() = _albums

    private val _photos: MutableLiveData<List<Photo>> = MutableLiveData()
    val photos: LiveData<List<Photo>>
        get() = _photos

    private val _error: MutableLiveData<String> = MutableLiveData("")
    val error: LiveData<String>
        get() = _error

    private val _navigateTo: MutableLiveData<Class<*>> = MutableLiveData()
    val navigateTo: LiveData<Class<*>>
        get() = _navigateTo


    fun fetchAlbumsFromApi() = viewModelScope.launch {
        repo.fetchAlbums().let {
            when (it) {
                is Result.Success<*> -> {
                    if (it.data != null && (it.data as List<Album>).isNotEmpty()) {
                        _albums.postValue(it.data)
                    } else {
                        _error.postValue("Null or Empty Response")
                    }

                }
                is Result.Error -> {
                    _error.postValue(it.error)
                }
            }
        }
    }

    private fun fetchPhotosFromApi(id: Int) = viewModelScope.launch {
        repo.fetchPhotos(id).let {
            when (it) {
                is Result.Success<*> -> {
                    if (it.data != null && (it.data as List<Photo>).isNotEmpty()) {
                        _photos.postValue(it.data)
                        _navigateTo.postValue(PhotosFragment::class.java)
                    } else {
                        _error.postValue("Null or Empty Response")
                    }

                }
                is Result.Error -> {
                    _error.postValue(it.error)
                }
            }
        }
    }

    override fun onItemClick(album: Album) {
        fetchPhotosFromApi(album.userId)
    }
}