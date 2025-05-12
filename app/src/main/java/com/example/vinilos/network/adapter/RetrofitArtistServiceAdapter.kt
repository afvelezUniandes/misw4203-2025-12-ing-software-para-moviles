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

                val result = ArrayList<Musician>(musicians.size)
                var performerPrizes: List<PerformerPrize>

                for (musician in musicians) {

                    performerPrizes = musician.performerPrizes.map {
                        PerformerPrize(id = it.id, premiationDate = it.premiationDate)
                    }

                    result.add(
                        Musician(
                            id = musician.id,
                            name = musician.name,
                            image = musician.image,
                            description = musician.description,
                            birthDate = musician.birthDate,
                            albums = musician.albums,
                            performerPrizes = performerPrizes
                        )
                    )
                }
                Result.success(result)
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

                val result = ArrayList<Band>(bands.size)
                var bandMusicians: List<Musician>
                var musicianPrizes: List<PerformerPrize>
                var bandPrizes: List<PerformerPrize>

                for (band in bands) {

                    bandMusicians = ArrayList<Musician>(band.musicians.size)
                    for (musician in band.musicians) {
                        musicianPrizes = musician.performerPrizes.map {
                            PerformerPrize(id = it.id, premiationDate = it.premiationDate)
                        }

                        bandMusicians.add(
                            Musician(
                                id = musician.id,
                                name = musician.name,
                                image = musician.image,
                                description = musician.description,
                                birthDate = musician.birthDate,
                                albums = musician.albums,
                                performerPrizes = musicianPrizes
                            )
                        )
                    }

                    bandPrizes = band.performerPrizes.map {
                        PerformerPrize(id = it.id, premiationDate = it.premiationDate)
                    }

                    result.add(
                        Band(
                            id = band.id,
                            name = band.name,
                            image = band.image,
                            description = band.description,
                            creationDate = band.creationDate,
                            albums = band.albums,
                            musicians = bandMusicians,
                            performerPrizes = bandPrizes
                        )
                    )
                }
                Result.success(result)
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

            val performerPrizes = musician.performerPrizes.map {
                PerformerPrize(id = it.id, premiationDate = it.premiationDate)
            }

            Result.success(
                Musician(
                    id = musician.id,
                    name = musician.name,
                    image = musician.image,
                    description = musician.description,
                    birthDate = musician.birthDate,
                    albums = musician.albums,
                    performerPrizes = performerPrizes
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

            val bandMusicians = ArrayList<Musician>(band.musicians.size)
            var musicianPrizes: List<PerformerPrize>

            for (musician in band.musicians) {
                musicianPrizes = musician.performerPrizes.map {
                    PerformerPrize(id = it.id, premiationDate = it.premiationDate)
                }

                bandMusicians.add(
                    Musician(
                        id = musician.id,
                        name = musician.name,
                        image = musician.image,
                        description = musician.description,
                        birthDate = musician.birthDate,
                        albums = musician.albums,
                        performerPrizes = musicianPrizes
                    )
                )
            }

            val bandPrizes = band.performerPrizes.map {
                PerformerPrize(id = it.id, premiationDate = it.premiationDate)
            }

            Result.success(
                Band(
                    id = band.id,
                    name = band.name,
                    image = band.image,
                    description = band.description,
                    creationDate = band.creationDate,
                    albums = band.albums,
                    musicians = bandMusicians,
                    performerPrizes = bandPrizes
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

