package com.example.vinilos.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinilos.models.Album
import com.example.vinilos.models.Track
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

    private val _showAddTrackDialog = MutableStateFlow(false)
    val showAddTrackDialog: StateFlow<Boolean> = _showAddTrackDialog

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

    fun showAddTrackDialog() {
        _showAddTrackDialog.value = true
    }

    fun hideAddTrackDialog() {
        _showAddTrackDialog.value = false
    }

    fun addTrack(name: String, duration: String) {
        val currentAlbum = _album.value ?: return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val newTrack = Track(
                    id = System.currentTimeMillis().toInt(),
                    name = name,
                    duration = duration
                )

                val updatedTracks = currentAlbum.tracks.toMutableList().apply {
                    add(newTrack)
                }

                _album.value = currentAlbum.copy(tracks = updatedTracks)
                hideAddTrackDialog()
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al agregar el track"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
