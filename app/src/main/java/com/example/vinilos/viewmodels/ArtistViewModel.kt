package com.example.vinilos.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinilos.models.Artist
import com.example.vinilos.models.Band
import com.example.vinilos.models.Musician
import com.example.vinilos.repositories.ArtistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArtistViewModel(private val repository: ArtistRepository) : ViewModel() {

    private val _musicians = MutableStateFlow<List<Musician>>(emptyList())
    val musicians: StateFlow<List<Musician>> = _musicians

    private val _bands = MutableStateFlow<List<Band>>(emptyList())
    val bands: StateFlow<List<Band>> = _bands

    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    val artists: StateFlow<List<Artist>> = _artists

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadMusicians() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.getMusicians()
                .onSuccess { _musicians.value = it }
                .onFailure { _error.value = it.message }

            _isLoading.value = false
        }
    }

    fun loadBands() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.getBands()
                .onSuccess { _bands.value = it }
                .onFailure { _error.value = it.message }

            _isLoading.value = false
        }
    }

    fun loadAllArtists() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.getAllArtists()
                .onSuccess { _artists.value = it }
                .onFailure { _error.value = it.message }

            _isLoading.value = false
        }
    }
}
