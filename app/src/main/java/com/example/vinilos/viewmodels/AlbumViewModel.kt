package com.example.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinilos.models.Album
import com.example.vinilos.repositories.AlbumRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlbumViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AlbumRepository()

    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>> = _albums

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadAlbums()
    }

    fun refreshAlbums() {
        loadAlbums()
    }

    fun loadAlbums() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val result = repository.getAlbumsAsList()
                _albums.value = result
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al cargar los álbumes"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
