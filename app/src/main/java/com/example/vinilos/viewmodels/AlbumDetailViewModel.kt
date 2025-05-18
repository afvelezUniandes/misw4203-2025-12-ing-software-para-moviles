package com.example.vinilos.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinilos.models.Album
import com.example.vinilos.models.TrackDTO
import com.example.vinilos.repositories.AlbumRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlbumDetailViewModel(
    private val repository: AlbumRepository = AlbumRepository()
) : ViewModel() {

    private val _album = MutableStateFlow<Album?>(null)
    val album: StateFlow<Album?> = _album.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadAlbumDetail(albumId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val result = repository.getAlbumById(albumId)
                if (result.isSuccess) {
                    _album.value = result.getOrNull()
                } else {
                    _error.value = result.exceptionOrNull()?.message ?: "Error desconocido"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addTrack(albumId: Int, name: String, duration: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val trackDTO = TrackDTO(name, duration)
                val result = repository.addTrackToAlbum(albumId, trackDTO)

                if (result.isSuccess) {
                    loadAlbumDetail(albumId)
                } else {
                    _error.value = result.exceptionOrNull()?.message ?: "Error al agregar el track"
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
                _isLoading.value = false
            }
        }
    }
}
