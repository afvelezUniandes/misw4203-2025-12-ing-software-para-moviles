package com.example.vinilos.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinilos.models.Album
import com.example.vinilos.repositories.AlbumRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlbumDetailViewModel(private val repository: AlbumRepository) : ViewModel() {
    private val _album = MutableStateFlow<Album?>(null)
    val album: StateFlow<Album?> = _album

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadAlbumDetail(albumId: Int) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                repository.getAlbumById(albumId).fold(
                    onSuccess = { album ->
                        _album.value = album
                        _error.value = null
                    },
                    onFailure = { exception ->
                        _error.value = exception.message ?: "Error desconocido al cargar el álbum"
                    }
                )
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido al cargar el álbum"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
