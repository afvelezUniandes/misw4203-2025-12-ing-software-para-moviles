package com.example.vinilos.network.adapter

import com.example.vinilos.models.Album

interface AlbumServiceAdapter {
    suspend fun getAlbums(): Result<List<Album>>
    suspend fun getAlbumById(id: Int): Result<Album>
}