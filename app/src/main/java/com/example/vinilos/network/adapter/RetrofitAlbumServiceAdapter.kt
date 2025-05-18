package com.example.vinilos.network.adapter

import com.example.vinilos.models.*
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
            Result.success(apiService.getAlbumById(id))
        } catch (e: IOException) {
            Result.failure(Exception("Error de red o servidor: ${e.message}", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error al obtener el álbum: ${e.message}", e))
        }
    }

    override suspend fun createAlbum(albumDTO: AlbumDTO): Result<Album> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.createAlbum(albumDTO)
            if (response.isSuccessful) {
                Result.success(response.body() ?: throw Exception("Respuesta vacía del servidor"))
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Error de red o servidor: ${e.message}", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error al crear el álbum: ${e.message}", e))
        }
    }

    override suspend fun addTrackToAlbum(albumId: Int, trackDTO: TrackDTO): Result<Track>  = withContext(Dispatchers.IO) {
        try {
            val response = apiService.addTrackToAlbum(albumId, trackDTO)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}