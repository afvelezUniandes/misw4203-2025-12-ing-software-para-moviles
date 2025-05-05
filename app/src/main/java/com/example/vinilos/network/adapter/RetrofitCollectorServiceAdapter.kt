package com.example.vinilos.network.adapter

import com.example.vinilos.models.Collector
import com.example.vinilos.network.ApiService
import com.example.vinilos.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class RetrofitCollectorServiceAdapter : CollectorServiceAdapter {
    private val apiService: ApiService = RetrofitInstance.apiService

    override suspend fun getCollectors(): Result<List<Collector>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getCollectors()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Error de red o servidor: ${e.message}", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error al obtener coleccionistas: ${e.message}", e))
        }
    }

    override suspend fun getCollectorById(id: Int): Result<Collector> = withContext(Dispatchers.IO) {
        try {
            val collector = apiService.getCollectorById(id)
            Result.success(collector)
        } catch (e: IOException) {
            Result.failure(Exception("Error de red o servidor: ${e.message}", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error al obtener el coleccionista: ${e.message}", e))
        }
    }
}
