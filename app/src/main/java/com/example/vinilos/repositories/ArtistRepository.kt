package com.example.vinilos.repositories

import com.example.vinilos.models.Artist
import com.example.vinilos.models.Band
import com.example.vinilos.models.Musician
import com.example.vinilos.network.ApiService

class ArtistRepository(private val apiService: ApiService) {

    suspend fun getMusicians(): Result<List<Musician>> {
        return try {
            val response = apiService.getMusicians()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getBands(): Result<List<Band>> {
        return try {
            val response = apiService.getBands()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAllArtists(): Result<List<Artist>> {
        val musicians = getMusicians()
        val bands = getBands()

        val artistList = mutableListOf<Artist>()

        musicians.getOrNull()?.forEach { musician ->
            artistList.add(Artist(
                id = musician.id,
                name = musician.name,
                image = musician.image,
                description = musician.description,
                date = musician.birthDate,
                dateType = "Birth",
                type = "Musician",
                albums = musician.albums
            ))
        }

        bands.getOrNull()?.forEach { band ->
            artistList.add(Artist(
                id = band.id,
                name = band.name,
                image = band.image,
                description = band.description,
                date = band.creationDate,
                dateType = "Formation",
                type = "Band",
                albums = band.albums
            ))
        }

        return Result.success(artistList)
    }
}

