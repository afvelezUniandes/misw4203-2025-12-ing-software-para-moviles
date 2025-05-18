package com.example.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinilos.models.AlbumDTO
import com.example.vinilos.repositories.AlbumRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CreateAlbumViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AlbumRepository()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess

    fun createAlbum(
        name: String,
        cover: String,
        releaseDate: String,
        description: String,
        genre: String,
        recordLabel: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _isSuccess.value = false

            try {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date = dateFormat.parse(releaseDate) ?: Date()

                val albumDTO = AlbumDTO(
                    name = name,
                    cover = cover,
                    releaseDate = date,
                    description = description,
                    genre = genre,
                    recordLabel = recordLabel
                )

                val result = repository.createAlbum(albumDTO)
                if (result.isSuccess) {
                    _isSuccess.value = true
                } else {
                    _error.value = result.exceptionOrNull()?.message ?: "Error al crear el álbum"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al crear el álbum"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
