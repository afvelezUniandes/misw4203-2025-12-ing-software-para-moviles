package com.example.vinilos.network.adapter

import com.example.vinilos.models.Album
import com.example.vinilos.models.Track
import com.example.vinilos.network.ApiService
import com.example.vinilos.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class RetrofitAlbumServiceAdapter : AlbumServiceAdapter {
    private val apiService: ApiService = RetrofitInstance.apiService

    override suspend fun getAlbums(): Result<List<Album>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAlbums()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Error de red o servidor: ${e.message}", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error al obtener álbumes: ${e.message}", e))
        }
    }

    override suspend fun getAlbumById(id: Int): Result<Album> = withContext(Dispatchers.IO) {
        try {
            val album = apiService.getAlbumById(id)
            Result.success(album)
        } catch (e: IOException) {
            Result.failure(Exception("Error de red o servidor: ${e.message}", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error al obtener el álbum: ${e.message}", e))
        }
    }
}