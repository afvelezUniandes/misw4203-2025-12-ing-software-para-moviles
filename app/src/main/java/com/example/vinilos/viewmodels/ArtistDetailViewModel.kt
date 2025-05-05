package com.example.vinilos.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinilos.models.Artist
import com.example.vinilos.repositories.ArtistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArtistDetailViewModel(private val repository: ArtistRepository) : ViewModel() {
    private val _artist = MutableStateFlow<Artist?>(null)
    val artist: StateFlow<Artist?> = _artist

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadArtistDetail(artistId: Int) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val artists = repository.getAllArtists().getOrNull() ?: emptyList()
                val foundArtist = artists.find { it.id == artistId }

                if (foundArtist != null) {
                    _artist.value = foundArtist
                    _error.value = null
                } else {
                    _error.value = "No se encontró el artista con ID $artistId"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido al cargar el artista"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
