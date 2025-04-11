package com.example.vinilos.repositories

import com.example.vinilos.models.Album
import com.example.vinilos.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumRepository {
    private val apiService = RetrofitInstance.apiService

    suspend fun getAlbums(): List<Album> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAlbums()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                throw Exception("Error ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            throw Exception("Error al obtener álbumes: ${e.message}")
        }
    }

    suspend fun getAlbumById(id: Int): Result<Album> = withContext(Dispatchers.IO) {
        try {
            val album = apiService.getAlbumById(id)
            Result.success(album)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
