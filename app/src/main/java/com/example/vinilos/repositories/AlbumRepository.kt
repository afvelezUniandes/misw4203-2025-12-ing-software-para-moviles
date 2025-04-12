package com.example.vinilos.repositories

import com.example.vinilos.models.Album
import com.example.vinilos.models.Track
import com.example.vinilos.network.adapter.AlbumServiceAdapter
import com.example.vinilos.network.adapter.RetrofitAlbumServiceAdapter

class AlbumRepository(
    private val serviceAdapter: AlbumServiceAdapter = RetrofitAlbumServiceAdapter()
) {
    suspend fun getAlbums(): List<Album> {
        val result = serviceAdapter.getAlbums()
        return result.getOrElse { throw it }
    }

    suspend fun getAlbumById(id: Int): Result<Album> {
        return serviceAdapter.getAlbumById(id)
    }
}
