package com.example.vinilos.network.adapter

import com.example.vinilos.models.*
import com.example.vinilos.network.ApiService
import com.example.vinilos.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class RetrofitArtistServiceAdapter : ArtistServiceAdapter {
    private val apiService: ApiService = RetrofitInstance.apiService

    override suspend fun getMusicians(): Result<List<Musician>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getMusicians()
            if (response.isSuccessful) {
                val musicians = response.body() ?: emptyList()
                Result.success(musicians.map { musician ->
                    Musician(
                        id = musician.id,
                        name = musician.name,
                        image = musician.image,
                        description = musician.description,
                        birthDate = musician.birthDate,
                        albums = musician.albums,
                        performerPrizes = musician.performerPrizes.map {
                            PerformerPrize(
                                id = it.id,
                                premiationDate = it.premiationDate
                            )
                        }
                    )
                })
            } else {
                Result.failure(Exception("Error HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Error de red o servidor: ${e.message}", e))
        } catch (e: HttpException) {
            Result.failure(Exception("Error HTTP ${e.code()}: ${e.message()}", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error al obtener músicos: ${e.message}", e))
        }
    }

    override suspend fun getBands(): Result<List<Band>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getBands()
            if (response.isSuccessful) {
                val bands = response.body() ?: emptyList()
                Result.success(bands.map { band ->
                    Band(
                        id = band.id,
                        name = band.name,
                        image = band.image,
                        description = band.description,
                        creationDate = band.creationDate,
                        albums = band.albums,
                        musicians = band.musicians.map { musician ->
                            Musician(
                                id = musician.id,
                                name = musician.name,
                                image = musician.image,
                                description = musician.description,
                                birthDate = musician.birthDate,
                                albums = musician.albums,
                                performerPrizes = musician.performerPrizes.map {
                                    PerformerPrize(
                                        id = it.id,
                                        premiationDate = it.premiationDate
                                    )
                                }
                            )
                        },
                        performerPrizes = band.performerPrizes.map {
                            PerformerPrize(
                                id = it.id,
                                premiationDate = it.premiationDate
                            )
                        }
                    )
                })
            } else {
                Result.failure(Exception("Error HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Error de red o servidor: ${e.message}", e))
        } catch (e: HttpException) {
            Result.failure(Exception("Error HTTP ${e.code()}: ${e.message()}", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error al obtener bandas: ${e.message}", e))
        }
    }

    override suspend fun getMusicianById(id: Int): Result<Musician> = withContext(Dispatchers.IO) {
        try {
                val musician = apiService.getMusicianById(id)
                Result.success(
                    Musician(
                        id = musician.id,
                        name = musician.name,
                        image = musician.image,
                        description = musician.description,
                        birthDate = musician.birthDate,
                        albums = musician.albums,
                        performerPrizes = musician.performerPrizes.map {
                            PerformerPrize(
                                id = it.id,
                                premiationDate = it.premiationDate
                            )
                        }
                    )
                )
        } catch (e: IOException) {
            Result.failure(Exception("Error de red o servidor: ${e.message}", e))
        } catch (e: HttpException) {
            Result.failure(Exception("Error HTTP ${e.code()}: ${e.message()}", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error al obtener el músico: ${e.message}", e))
        }
    }

    override suspend fun getBandById(id: Int): Result<Band> = withContext(Dispatchers.IO) {
        try {
                val band = apiService.getBandById(id)
                Result.success(
                    Band(
                        id = band.id,
                        name = band.name,
                        image = band.image,
                        description = band.description,
                        creationDate = band.creationDate,
                        albums = band.albums,
                        musicians = band.musicians.map { musician ->
                            Musician(
                                id = musician.id,
                                name = musician.name,
                                image = musician.image,
                                description = musician.description,
                                birthDate = musician.birthDate,
                                albums = musician.albums,
                                performerPrizes = musician.performerPrizes.map {
                                    PerformerPrize(
                                        id = it.id,
                                        premiationDate = it.premiationDate
                                    )
                                }
                            )
                        },
                        performerPrizes = band.performerPrizes.map {
                            PerformerPrize(
                                id = it.id,
                                premiationDate = it.premiationDate
                            )
                        }
                    )
                )
        } catch (e: IOException) {
            Result.failure(Exception("Error de red o servidor: ${e.message}", e))
        } catch (e: HttpException) {
            Result.failure(Exception("Error HTTP ${e.code()}: ${e.message()}", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error al obtener la banda: ${e.message}", e))
        }
    }
}
