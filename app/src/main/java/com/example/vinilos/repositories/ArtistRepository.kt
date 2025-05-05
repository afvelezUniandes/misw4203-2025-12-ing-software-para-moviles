package com.example.vinilos.repositories

import com.example.vinilos.models.Artist
import com.example.vinilos.network.adapter.ArtistServiceAdapter
import com.example.vinilos.network.adapter.RetrofitArtistServiceAdapter

class ArtistRepository(
    private val serviceAdapter: ArtistServiceAdapter = RetrofitArtistServiceAdapter()
) {
    suspend fun getMusicians() = serviceAdapter.getMusicians()

    suspend fun getBands() = serviceAdapter.getBands()

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
                albums = musician.albums,
                performerPrizes = musician.performerPrizes
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
                albums = band.albums,
                performerPrizes = band.performerPrizes
            ))
        }

        return Result.success(artistList)
    }
}
