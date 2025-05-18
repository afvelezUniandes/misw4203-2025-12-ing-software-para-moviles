package com.example.vinilos.repositories

import com.example.vinilos.models.Album
import com.example.vinilos.models.AlbumDTO
import com.example.vinilos.network.adapter.AlbumServiceAdapter
import com.example.vinilos.network.adapter.RetrofitAlbumServiceAdapter
import com.example.vinilos.network.cache.CacheManager

class AlbumRepository(
    private val serviceAdapter: AlbumServiceAdapter = RetrofitAlbumServiceAdapter()
) {
    private val cacheManager = CacheManager.getInstance()

    suspend fun getAlbums(): Result<List<Album>> {

            val cachedAlbums = cacheManager.getAlbums()

        if (cachedAlbums != null) {
            return Result.success(cachedAlbums)
        }

        return serviceAdapter.getAlbums().also { result ->
            if (result.isSuccess) {
                result.getOrNull()?.let { albums ->
                    cacheManager.addAlbums(albums)
                }
            }
        }
    }

    suspend fun getAlbumsAsList(): List<Album> {
        val result = getAlbums()
        return result.getOrElse { throw it }
    }

    suspend fun getAlbumById(id: Int): Result<Album> {
        val cachedAlbum = cacheManager.getAlbumById(id)

        if (cachedAlbum != null) {
            return Result.success(cachedAlbum)
        }

        return serviceAdapter.getAlbumById(id).also { result ->
            if (result.isSuccess) {
                result.getOrNull()?.let { album ->
                    cacheManager.addAlbum(id, album)
                }
            }
        }
    }

    suspend fun createAlbum(albumDTO: AlbumDTO): Result<Album> {
        return serviceAdapter.createAlbum(albumDTO).also { result ->
            if (result.isSuccess) {
                cacheManager.clearAlbumsCache()
            }
        }
    }
}

