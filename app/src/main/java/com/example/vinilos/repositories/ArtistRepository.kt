package com.example.vinilos.repositories

import com.example.vinilos.models.Artist
import com.example.vinilos.network.adapter.ArtistServiceAdapter
import com.example.vinilos.network.adapter.RetrofitArtistServiceAdapter
import com.example.vinilos.network.cache.CacheManager

class ArtistRepository(
    private val serviceAdapter: ArtistServiceAdapter = RetrofitArtistServiceAdapter()
) {
    private val cacheManager = CacheManager.getInstance()

    suspend fun getMusicians() = cacheManager.getMusicians()?.let { musicians ->
        Result.success(musicians)
    } ?: run {
        serviceAdapter.getMusicians().also { result ->
            if (result.isSuccess) {
                result.getOrNull()?.let { musicians ->
                    cacheManager.addMusicians(musicians)
                }
            }
        }
    }

    suspend fun getBands() = cacheManager.getBands()?.let { bands ->
        Result.success(bands)
    } ?: run {
        serviceAdapter.getBands().also { result ->
            if (result.isSuccess) {
                result.getOrNull()?.let { bands ->
                    cacheManager.addBands(bands)
                }
            }
        }
    }

    suspend fun getAllArtists(): Result<List<Artist>> {
        val musicians = getMusicians()
        val bands = getBands()

        val musiciansList = musicians.getOrNull()
        val bandsList = bands.getOrNull()

        val totalSize = (musiciansList?.size ?: 0) + (bandsList?.size ?: 0)
        val artistList = ArrayList<Artist>(totalSize)

        musiciansList?.let { musicians ->
            for (musician in musicians) {
                artistList.add(Artist(
                    id = musician.id,
                    name = musician.name,
                    image = musician.image,
                    description = musician.description,
                    date = musician.birthDate,
                    dateType = BIRTH_DATE_TYPE,
                    type = MUSICIAN_TYPE,
                    albums = musician.albums,
                    performerPrizes = musician.performerPrizes
                ))
            }
        }

        bandsList?.let { bands ->
            for (band in bands) {
                artistList.add(Artist(
                    id = band.id,
                    name = band.name,
                    image = band.image,
                    description = band.description,
                    date = band.creationDate,
                    dateType = FORMATION_DATE_TYPE,
                    type = BAND_TYPE,
                    albums = band.albums,
                    performerPrizes = band.performerPrizes
                ))
            }
        }

        return Result.success(artistList)
    }

    companion object {
        private const val BIRTH_DATE_TYPE = "Birth"
        private const val FORMATION_DATE_TYPE = "Formation"
        private const val MUSICIAN_TYPE = "Musician"
        private const val BAND_TYPE = "Band"
    }
}
