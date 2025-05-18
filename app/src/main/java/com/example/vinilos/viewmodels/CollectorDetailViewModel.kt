package com.example.vinilos.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinilos.models.Collector
import com.example.vinilos.repositories.CollectorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CollectorDetailViewModel(
    private val repository: CollectorRepository
) : ViewModel() {
    private val _collector = MutableStateFlow<Collector?>(null)
    val collector: StateFlow<Collector?> = _collector

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadCollectorDetail(collectorId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = repository.getCollectorById(collectorId)
                result.fold(
                    onSuccess = { collector ->
                        _collector.value = collector
                    },
                    onFailure = { exception ->
                        _error.value = exception.message ?: "Error desconocido"
                    }
                )
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
